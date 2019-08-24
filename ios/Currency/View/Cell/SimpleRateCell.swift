//
//  SimpleRateCell.swift
//  Currency
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import UIKit
import MVICocoa
import RxSwift
import RxCocoa

class SimpleRateCell: UITableViewCell, RateChangeDelegate, RatesChangeDelegate {

	@IBOutlet weak var viewCountryFlagImage: UIImageView!
	@IBOutlet weak var viewCurrencyCodeLabel: UILabel!
	@IBOutlet weak var viewCurrencyNameLabel: UILabel!
	
	@IBOutlet weak var viewAmountTextField: UITextField!
	@IBOutlet weak var viewBackgroundButton: UIButton!
	
	var currencyToFlagUrlManager: CurrencyToFlagUrlManager!
	var rateManager: RateManager!
	
  private let disposeBag = CompositeDisposeBag()

	private var entity: RateEntity = .empty
	
	private var responderProperty = AtomicProperty(defaultValue: false)
	
  override func prepareForReuse() {
    super.prepareForReuse()
		responderProperty.value = false
		self.entity = .empty
    disposeBag.clear()
		rateManager.removeRateChangeDelegate(self)
		rateManager.removeRatesChangeDelegate(self)
  }

  func bind(entity: RateEntity) {
		self.entity = entity
		
		let amountString = rateManager.format(entity.amount)
		if entity.amount != 0.0 {
			viewAmountTextField.text = amountString
		}
		
		rateManager.addRateChangeDelegate(self)
		rateManager.addRatesChangeDelegate(self)
		
		let uri = currencyToFlagUrlManager.countryFlagUrlFor(currencyCode: entity.base)
		viewCountryFlagImage.loadCircularCrop(uri.toUrl())
		
		viewCurrencyCodeLabel.text = entity.base
		viewCurrencyNameLabel.text = entity.base.toCurrencyName()
		
		
		disposeBag += bindNewRate(entity: entity).subscribe(onNext: { [unowned self] newRate in self.rateManager.rate = newRate })
		disposeBag += bindRateAmountChange(entity: entity).subscribe(onNext: { [unowned self] newAmount in self.rateManager.rate = newAmount })
		
		maybeUpdateCurrencies()
  }
	
	func rateChange(newValue: RateEntity) {
		requestFocusIfNeeded(newValue: newValue)
	}
	
	func ratesChange(newRates: Dictionary<String, Double>) {
		maybeUpdateCurrencies()
	}
	
	private func bindRateAmountChange(entity: RateEntity) -> Observable<RateEntity> {
		return viewAmountTextField.rx.text.asObservable()
			.observeOn(MainScheduler.asyncInstance)
			.filter { [unowned self] _ in entity == self.rateManager.rate }
			.map { [unowned self] text in
				entity.amount = self.rateManager.parse(text ?? .empty)
				return entity
			}
	}
	
	private func bindNewRate(entity: RateEntity) -> Observable<RateEntity> {
		return viewBackgroundButton.rx.tap.asObservable()
			.do(onNext: { [unowned self] _ in
				if entity == self.rateManager.rate {
					if !self.viewAmountTextField.isEditing {
					  self.viewAmountTextField.becomeFirstResponder()
					}
				}
			})
			.filter { [unowned self] _ in entity != self.rateManager.rate }
			.map { [unowned self] _ in
				let amount = self.rateManager.parse(self.viewAmountTextField.text ?? .empty)
				return RateEntity(entity.base, amount)
			}
	}
	
	private func requestFocusIfNeeded(newValue: RateEntity) {
		if entity == newValue && !responderProperty.value {
			viewAmountTextField.becomeFirstResponder()
			responderProperty.value = true
		}
		maybeUpdateCurrencies()
	}
	
	private func maybeUpdateCurrencies() {
		let amount = rateManager.amountFor(currencyCode: entity.base)
		let amountString = rateManager.format(amount)
		if entity != rateManager.rate {
			entity.amount = amount
			if amount == 0.0 {
				viewAmountTextField.text = nil
			} else {
				viewAmountTextField.text = amountString
			}
		}
	}
}

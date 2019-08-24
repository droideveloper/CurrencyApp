//
//  RateDataSource.swift
//  Currency
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import UIKit
import MVICocoa

class RateDataSource: TableDataSource<RateEntity>, UITableViewDelegate {

	private lazy var sizeMap = {
		return Dictionary<String, CGFloat>()
	}()
	
	private let rateManager: RateManager
	private let currencyToFlagUrlManager: CurrencyToFlagUrlManager
	
	public init(dataSet: ObservableList<RateEntity>, rateManager: RateManager, currencyToFlagUrlManager: CurrencyToFlagUrlManager) {
		self.rateManager = rateManager
		self.currencyToFlagUrlManager = currencyToFlagUrlManager
		super.init(dataSet: dataSet)
	}
	
  override func identifierAt(_ indexPath: IndexPath) -> String {
		return String(describing: SimpleRateCell.self)
	}
	
	func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
		let identifier = identifierAt(indexPath)
		if sizeMap.index(forKey: identifier) == nil {
			sizeMap[identifier] = cell.frame.size.height
		}
	}
	
	func tableView(_ tableView: UITableView, estimatedHeightForRowAt indexPath: IndexPath) -> CGFloat {
		let identifier = identifierAt(indexPath)
		return sizeMap[identifier] ?? UITableView.automaticDimension
	}
	
	override func bind(_ cell: UITableViewCell, _ item: RateEntity) {
		if let cell = cell as? SimpleRateCell {
			cell.rateManager = rateManager
			cell.currencyToFlagUrlManager = currencyToFlagUrlManager
			cell.bind(entity: item)
		}
	}
}

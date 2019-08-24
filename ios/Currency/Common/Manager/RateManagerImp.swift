//
//  RateManagerImp.swift
//  Currency
//
//  Created by Fatih Şen on 18.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa

public class RateManagerImp: RateManager {
	
	private var _rates: Dictionary<String, Double> = [:]
	private var _rate: RateEntity = RateEntity("EUR", 0.0)
	
	private var rateChangeDelegates = Array<RateChangeDelegate>()
	private var ratesChangeDelegates = Array<RatesChangeDelegate>()
	
	public var rate: RateEntity {
		get {
			return _rate
		}
		set {
			_rate = newValue
			notifyRateChange()
		}
	}
	
	public var rates: Dictionary<String, Double> {
		get {
			return _rates
		}
		set {
			_rates = newValue
			notifyRatesChange()
		}
	}
	
	public var amount: Double {
		get {
			return _rate.amount
		}
	}
	
	private var dispatchOnQueue: Bool
	
	init(dispatchOnQueue: Bool = true) {
		self.dispatchOnQueue = dispatchOnQueue
	}
	
	public func format(_ amount: Double) -> String {
		return String(format: "%.2f", amount)
	}
	
	public func amountFor(currencyCode: String) -> Double {
		return (_rates[currencyCode] ?? 0.0) * _rate.amount
	}
	
	public func parse(_ text: String) -> Double {
		guard let amount = Double(text) else {
			return 0.0
		}
		return amount
	}
	
	public func clearAll() {
		_rates = [:]
		_rate = .empty
	}
	
	public func addRateChangeDelegate(_ delegate: RateChangeDelegate) {
		rateChangeDelegates.append(delegate)
	}
	
	public func removeRateChangeDelegate(_ delegate: RateChangeDelegate) {
		let index = rateChangeDelegates.firstIndex { d in d === delegate } ?? -1
		if index != -1 {
			rateChangeDelegates.remove(at: index)
		}
	}
	
	public func addRatesChangeDelegate(_ delegate: RatesChangeDelegate) {
		ratesChangeDelegates.append(delegate)
	}
	
	public func removeRatesChangeDelegate(_ delegate: RatesChangeDelegate) {
		let index = ratesChangeDelegates.firstIndex { d in d === delegate } ?? -1
		if index != -1 {
			ratesChangeDelegates.remove(at: index)
		}
	}
	
	private func notifyRateChange() {
		rateChangeDelegates.forEach { delegate in
			if dispatchOnQueue {
				DispatchQueue.main.async { [unowned self] in
					delegate.rateChange(newValue: self._rate)
				}
			} else {
				delegate.rateChange(newValue: self._rate)
			}
		}
	}
	
	private func notifyRatesChange() {
		ratesChangeDelegates.forEach { delegate in
			if dispatchOnQueue {
				DispatchQueue.main.async { [unowned self] in
					delegate.ratesChange(newRates: self._rates)
				}
			} else {
				delegate.ratesChange(newRates: self._rates)
			}
		}
	}
}

//
//  RateManager.swift
//  Currency
//
//  Created by Fatih Şen on 18.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public protocol RateManager {
	
	var rate: RateEntity { get set }
	var rates: Dictionary<String, Double> { get set }
	
	var amount: Double { get }
	
	func parse(_ text: String) -> Double
	func format(_ amount: Double) -> String

	func amountFor(currencyCode: String) -> Double
	
	func addRateChangeDelegate(_ delegate: RateChangeDelegate)
	func removeRateChangeDelegate( _ delegate: RateChangeDelegate)
	
	func addRatesChangeDelegate(_ delegate: RatesChangeDelegate)
	func removeRatesChangeDelegate(_ delegate: RatesChangeDelegate)
}

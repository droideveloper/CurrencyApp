//
//  MockRatesChangeCallback.swift
//  CurrencyTests
//
//  Created by Fatih Şen on 25.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

@testable import Currency

class MockRatesChangeCallback: RatesChangeDelegate {
	
	private let block: (Dictionary<String, Double>) -> Void
	
	init(block: @escaping (Dictionary<String, Double>) -> Void) {
		self.block = block
	}
	
	func ratesChange(newRates: Dictionary<String, Double>) {
		block(newRates)
	}
}

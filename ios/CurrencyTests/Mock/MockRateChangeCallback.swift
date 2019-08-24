//
//  MockRateChangeCallback.swift
//  CurrencyTests
//
//  Created by Fatih Şen on 25.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

@testable import Currency

class MockRateChangeCallback: RateChangeDelegate {
	
	private let block: (RateEntity) -> Void

	init(block: @escaping (RateEntity) -> Void) {
		self.block = block
	}
	
	func rateChange(newValue: RateEntity) {
		block(newValue)
	}
}

//
//  RateManagerTest.swift
//  CurrencyTests
//
//  Created by Fatih Şen on 25.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import XCTest
import MVICocoa
import RxSwift
import RxCocoa
import Swinject

@testable import Currency

class RateManagerTest: XCTestCase {
	
	private var rateManager: RateManager!
	
	override func setUp() {
		let mockApp = MockApp.shared
		guard let rateManager = mockApp.container.resolve(RateManager.self) else {
			fatalError("can not resolve \(RateManager.self)")
		}
		
		self.rateManager = rateManager
	}
	
	func testRateChangeDelegate() {
		let letch = CountDownLatch(count: 1)
		
		let rate = RateEntity("EUR", 1.0)
		
		let block: (RateEntity) -> Void = { newRate in
			
			assert(newRate == rate)
			
			letch.countDown()
		}
		
		let callback = MockRateChangeCallback(block: block)
		rateManager.addRateChangeDelegate(callback)
		
		rateManager.rate = rate
		
		letch.await()
		
		rateManager.removeRateChangeDelegate(callback)
	}
	
	func testRatesChangeDelegate() {
		let letch = CountDownLatch(count: 1)
		
		var rates = Dictionary<String, Double>()
		rates["EUR"] = 1.0
		
		let block: (Dictionary<String, Double>) -> Void = { newRates in
			
			assert(newRates == rates)
			
			letch.countDown()
		}
		
		let callback = MockRatesChangeCallback(block: block)
		rateManager.addRatesChangeDelegate(callback)
		
		rateManager.rates = rates
	
		letch.await()
	
		rateManager.removeRatesChangeDelegate(callback)
	}
	
	override func tearDown() {
		rateManager.clearAll()
	}
}

//
//  RateViewTest.swift
//  CurrencyTests
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import XCTest
import Swinject

@testable import Currency

class RateViewTest: XCTestCase {
	
	var rateView: MockRateView!
	var rateManager: RateManager!
	
	override func setUp() {
		let mockApp = MockApp.shared
		guard let rateView = mockApp.container.resolve(MockRateView.self) else {
			fatalError("can not resolve \(RateView.self)")
		}
		guard let rateManager = mockApp.container.resolve(RateManager.self) else {
			fatalError("can not resolve \(RateManager.self)")
		}
		
		self.rateView = rateView
		self.rateManager = rateManager
	}
	
	func testRateController() {
		let letch = CountDownLatch(count: 1)
		
		let block = { [unowned self] in
			assert(!self.rateManager.rates.isEmpty)
			
			letch.countDown()
			
			self.rateView.detach()
		}
		
		assert(rateManager.rates.isEmpty)
		assert(!rateView.showProgress.value)
		rateView.block = block
		rateView.attach()
		
		assert(rateView.showProgress.value)
		
		letch.await()
	}
	
	override func tearDown() {
		rateManager.clearAll()
	}
}

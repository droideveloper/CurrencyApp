//
//  LandingPageView.swift
//  CurrencyTests
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import XCTest
import Swinject

@testable import Currency

class LandingPageViewTest: XCTestCase {
	
	var currencyToCountryManager: CurrencyToCountryManager!
	var landingPageView: MockLadingPageView!
	
	override func setUp() {
		let mockApp = MockApp.shared
		guard let landingPageView = mockApp.container.resolve(MockLadingPageView.self) else {
			fatalError("can not resolve \(MockLadingPageView.self)")
		}
		guard let currencyToCountryManager = mockApp.container.resolve(CurrencyToCountryManager.self) else {
			fatalError("can not resolve \(CurrencyToCountryManager.self)")
		}
		
		self.landingPageView = landingPageView
		self.currencyToCountryManager = currencyToCountryManager
	}
	
	func testLandingPageController() {
		let letch = CountDownLatch(count: 1)
		
		let block = { [unowned self] in
			assert(!self.currencyToCountryManager.needsPopulateData)
			
			letch.countDown()
			
			self.landingPageView.detach()
		}
		
		assert(currencyToCountryManager.needsPopulateData)
		assert(!landingPageView.showProgress.value)
		landingPageView.block = block
		landingPageView.attach()
		
		assert(landingPageView.showProgress.value)
		
		letch.await()
	}
}

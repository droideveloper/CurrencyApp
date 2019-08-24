//
//  UITestDependency.swift
//  CurrencyTests
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//


import Swinject
import MVICocoa

@testable import Currency

class UITestDependency: ControllerDependency {
	
	private let container: Container
	
	override init(container: Container) {
		self.container = container
		super.init(container: container)
	}
	
	override func setUp() {
		super.setUp()
		
		container.register(MockRateView.self) { resolver in
			guard let rateManager = resolver.resolve(RateManager.self) else {
				fatalError("can not resolve \(RateManager.self)")
			}
			guard let concurrency = resolver.resolve(Concurrency.self) else {
				fatalError("can not resolve \(Concurrency.self)")
			}
			let view = MockRateView()
			view.rateManager = rateManager
			view.viewModel = RateViewModel(view: view, concurrency: concurrency)
			return view
		}
		
		container.register(MockLadingPageView.self) { resolver in
			guard let currencyToCountryManager = resolver.resolve(CurrencyToCountryManager.self) else {
				fatalError("can not resolve \(CurrencyToCountryManager.self)")
			}
			guard let concurrency = resolver.resolve(Concurrency.self) else {
				fatalError("can not resolve \(Concurrency.self)")
			}
			let view = MockLadingPageView()
			view.currencyToCountryManager = currencyToCountryManager
			view.viewModel = LandingPageViewModel(view: view, concurrency: concurrency)
			return view
		}
	}
}

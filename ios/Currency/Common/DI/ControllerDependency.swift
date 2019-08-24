//
//  ControllerDependency.swift
//  Currency
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import Swinject
import SwinjectStoryboard
import MVICocoa

class ControllerDependency: Dependency {
	
	private let container: Container
	
	init(container: Container) {
		self.container = container
	}
	
	func setUp() {
		// dataSet
		container.register(ObservableList<RateEntity>.self) { _ in
			return ObservableList<RateEntity>()
		}
		// rateDataSource
		container.register(RateDataSource.self) { resolver in
			guard let dataSet = resolver.resolve(ObservableList<RateEntity>.self) else {
				fatalError("can not resolve \(ObservableList<RateEntity>.self)")
			}
			guard let rateManager = resolver.resolve(RateManager.self) else {
				fatalError("can not resolve \(RateManager.self)")
			}
			guard let currencyToFlagUrlManager = resolver.resolve(CurrencyToFlagUrlManager.self) else {
				fatalError("can not resolve \(CurrencyToFlagUrlManager.self)")
			}
			
			return RateDataSource(dataSet: dataSet, rateManager: rateManager, currencyToFlagUrlManager: currencyToFlagUrlManager)
		}
		// rateController
		container.storyboardInitCompleted(RateController.self) { resolver, controller in
			guard let concurrency = resolver.resolve(Concurrency.self) else {
				fatalError("can not resolve \(Concurrency.self)")
			}
			guard let dataSet = resolver.resolve(ObservableList<RateEntity>.self) else {
				fatalError("can not resolve \(ObservableList<RateEntity>.self)")
			}
			guard let dataSource = resolver.resolve(RateDataSource.self) else {
				fatalError("can not resolve \(RateDataSource.self)")
			}
			guard let rateManager = resolver.resolve(RateManager.self) else {
				fatalError("can not resolve \(RateManager.self)")
			}
			controller.viewModel = RateViewModel(view: controller, concurrency: concurrency)
			controller.dataSet = dataSet
			controller.dataSource = dataSource
			controller.rateManager = rateManager
		}
		// landingPageController
		container.storyboardInitCompleted(LandingPageController.self) { resolver, controller in
			guard let concurrency = resolver.resolve(Concurrency.self) else {
				fatalError("can not resolve \(Concurrency.self)")
			}
			guard let currencyToCountryManager = resolver.resolve(CurrencyToCountryManager.self) else {
				fatalError("can not resolve \(CurrencyToCountryManager.self)")
			}
			controller.currencyToCountryManager = currencyToCountryManager
			controller.viewModel = LandingPageViewModel(view: controller, concurrency: concurrency)
		}
	}
}

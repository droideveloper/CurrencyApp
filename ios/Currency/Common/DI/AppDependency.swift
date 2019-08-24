//
//  AppDependency.swift
//  Currency
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import Swinject

class AppDependency: Dependency {
	
	private let container: Container
	
	init(container: Container) {
		self.container = container
	}
	
	func setUp() {
		// endpoint
		container.register(Endpoint.self) { _ in return EndpointImp() }
			.inObjectScope(.container)
		// endpointProxy
		container.register(EndpointProxy.self) { resolver in
			guard let endpoint = resolver.resolve(Endpoint.self) else {
				fatalError("could not resolve \(Endpoint.self)")
			}
			return EndpointProxyImp(endpoint)
		}.inObjectScope(.container)
		// rateRepository
		container.register(RateRepository.self) { resolver in
			guard let proxy = resolver.resolve(EndpointProxy.self) else {
				fatalError("could not resolve \(EndpointProxy.self)")
			}
			return RateRepositoryImp(proxy)
		}.inObjectScope(.container)
		// countryCurrenciesRepository
		container.register(CountryCurrenciesRepository.self) { resolver in
			guard let proxy = resolver.resolve(EndpointProxy.self) else {
				fatalError("could not resolve \(EndpointProxy.self)")
			}
			return CountryCurrenciesRepositoryImp(proxy)
		}.inObjectScope(.container)
		// currencyToFlagUrlManager
		container.register(CurrencyToCountryManager.self) { resolver in
			return CurrencyToCountryManagerImp()
		}.inObjectScope(.container)
		// currencyToCountryManager
		container.register(CurrencyToFlagUrlManager.self) { resolver in
			guard let currencyToCountryManager = resolver.resolve(CurrencyToCountryManager.self) else {
				fatalError("could not resolve \(CurrencyToCountryManager.self)")
			}
			return CurrencyToFlagUrlManagerImp(currencyToCountryManager)
		}.inObjectScope(.container)
		// rateManager
		container.register(RateManager.self) { resolver in
			return RateManagerImp()
		}.inObjectScope(.container)
		// concurrency
		container.register(Concurrency.self) { resolve in
			return ConcurrencyImp()
		}.inObjectScope(.container)
	}
}

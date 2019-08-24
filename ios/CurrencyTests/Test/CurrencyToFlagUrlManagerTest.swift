//
//  CountryToFlagUrlManagerTest.swift
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

class CurrencyToFlagUrlManagerTest: XCTestCase {
	
	var currencyToFlagUrlManager: CurrencyToFlagUrlManager!
	var currencyToCountryManager: CurrencyToCountryManager!
	var countryCurrencyRepository: CountryCurrenciesRepository!
	
	var concurrency: Concurrency!
	
	lazy var disposeBag: CompositeDisposeBag = {
		return CompositeDisposeBag()
	}()
	
	override func setUp() {
		let mockApp = MockApp.shared
		guard let currencyToFlagUrlManager = mockApp.container.resolve(CurrencyToFlagUrlManager.self) else {
			fatalError("can not resolve \(CurrencyToFlagUrlManager.self)")
		}
		guard let currencyToCountryManager = mockApp.container.resolve(CurrencyToCountryManager.self) else {
			fatalError("can not resolve \(CurrencyToCountryManager.self)")
		}
		guard let countryCurrencyRepository = mockApp.container.resolve(CountryCurrenciesRepository.self) else {
			fatalError("can not resolve \(CountryCurrenciesRepository.self)")
		}
		guard let concurrency = mockApp.container.resolve(Concurrency.self) else {
			fatalError("can not resolve \(Concurrency.self)")
		}
		
		self.concurrency = concurrency
		self.currencyToCountryManager = currencyToCountryManager
		self.currencyToFlagUrlManager = currencyToFlagUrlManager
		self.countryCurrencyRepository = countryCurrencyRepository
	}
	
	func testCurrencyToFlagUrlManager() {
		let letch = CountDownLatch(count: 1)
		
		disposeBag += countryCurrencyRepository.countryCurrencies(.empty)
			.subscribeOn(concurrency.dispatchScheduler)
			.observeOn(concurrency.dispatchScheduler)
			.map { resource -> Dictionary<String, String> in
				switch resource {
				case .success(_, _, let data): return data ?? [:]
				case .failure(_): return [:]
				}
			}
			.subscribe(onNext: { [unowned self] data in
				self.currencyToCountryManager.populateCache(data)
				
				letch.countDown()
			})
		
		letch.await()
		
		let url = currencyToFlagUrlManager.countryFlagUrlFor(currencyCode: "EUR")
		assert(url != .empty)
	}
	
	override func tearDown() {
		disposeBag.clear()
		currencyToCountryManager.clearAll()
	}
}

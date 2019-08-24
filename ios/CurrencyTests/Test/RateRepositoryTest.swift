//
//  RateRepositoryTest.swift
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

class RateRepositoryTest: XCTestCase {
	
	var rateRepostiory: RateRepository!
	var concurrency: Concurrency!
	
	lazy var disposeBag: CompositeDisposeBag = {
		return CompositeDisposeBag()
	}()
	
	override func setUp() {
		let mockApp = MockApp.shared
		guard let rateRepository = mockApp.container.resolve(RateRepository.self) else {
			fatalError("can not resolve \(RateRepository.self)")
		}
		guard let concurrency = mockApp.container.resolve(Concurrency.self) else {
			fatalError("can not resolve \(Concurrency.self)")
		}
		
		self.concurrency = concurrency
		self.rateRepostiory = rateRepository
	}
	
	func testRateRepository() {
		let letch = CountDownLatch(count: 1)
		
		disposeBag += rateRepostiory.rates("EUR")
			.subscribeOn(concurrency.dispatchScheduler)
			.observeOn(concurrency.dispatchScheduler)
			.map { resource -> Bool in
				switch resource {
					case .success(_, _, data: _): return true
					case .failure(_): return false
				}
			}.subscribe(onNext: { expected in
				assert(expected)
				letch.countDown()
			})
		
		letch.await()
	}
	
	override func tearDown() {
		disposeBag.clear()
	}
	
}

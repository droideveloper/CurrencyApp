//
//  UnitTestDependency.swift
//  CurrencyTests
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import Swinject
import MVICocoa

@testable import Currency

class UnitTestDependency: AppDependency {
	
	private let container: Container
	
	override init(container: Container) {
		self.container = container
		super.init(container: container)
	}
	
	override func setUp() {
		super.setUp()
		
		container.register(Concurrency.self) { _ in
			return MockConcurrency()
		}.inObjectScope(.container)
		
		container.register(Endpoint.self) { _ in
			return MockEndpoint()
		}.inObjectScope(.container)
		
		container.register(RateManager.self) { _ in
			return RateManagerImp(dispatchOnQueue: false)
		}.inObjectScope(.container)
	}
}


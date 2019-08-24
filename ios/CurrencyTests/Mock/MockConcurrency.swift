//
//  MockConcurrency.swift
//  CurrencyTests
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift
import MVICocoa

class MockConcurrency: Concurrency {
	
	var dispatchScheduler: SchedulerType {
		get {
			return dispatcher
		}
	}
	
	private let dispatcher: SchedulerType
	
	public init() {
		self.dispatcher = MockScheduler()
	}
}

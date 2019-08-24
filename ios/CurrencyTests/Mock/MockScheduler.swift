//
//  MockScheduler.swift
//  CurrencyTests
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift

class MockScheduler: SchedulerType {
	
	var now: RxTime {
		get {
			return Date()
		}
	}
	
	func scheduleRelative<StateType>(_ state: StateType, dueTime: RxTimeInterval, action: @escaping (StateType) -> Disposable) -> Disposable {
		_ = action(state)
		return Disposables.create()
	}
	
	func schedule<StateType>(_ state: StateType, action: @escaping (StateType) -> Disposable) -> Disposable {
		_ = action(state)
		return Disposables.create()
	}
}

//
//  ConcurrencyImp.swift
//  Currency
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift
import MVICocoa

class ConcurrencyImp: Concurrency {
	
	var dispatchScheduler: SchedulerType {
		get {
			return MainScheduler.asyncInstance
		}
	}
}

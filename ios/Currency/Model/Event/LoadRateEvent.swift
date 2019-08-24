//
//  LoadRateEvent.swift
//  Currency
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import Swinject

class LoadRateEvent: Event {
	
	private let base: String
	
	init(base: String) {
		self.base = base
	}

  override func toIntent(container: Container?) -> Intent {
		guard let concurrency = container?.resolve(Concurrency.self) else {
			fatalError("can not resolve \(Concurrency.self)")
		}
		guard let rateRepository = container?.resolve(RateRepository.self) else {
			fatalError("can not resolve \(RateRepository.self)")
		}
    return LoadRateIntent(base: base,
													rateRepository: rateRepository,
													concurrency: concurrency)
  }
}

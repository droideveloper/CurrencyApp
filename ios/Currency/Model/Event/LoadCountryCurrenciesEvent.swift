//
//  LoadCountryCurrenciesEvent.swift
//  Currency
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import Swinject

class LoadCountryCurrenciesEvent: Event {
	
	private static let url: String = "http://country.io/currency.json"

  override func toIntent(container: Container?) -> Intent {
		guard let concurrency = container?.resolve(Concurrency.self) else {
			fatalError("can not resolve \(Concurrency.self)")
		}
		guard let countryCurrenciesRepository = container?.resolve(CountryCurrenciesRepository.self) else {
			fatalError("can not resolve \(CountryCurrenciesRepository.self)")
		}
    return LoadCountryCurrenciesIntent(url: LoadCountryCurrenciesEvent.url,
																			 countryCurrenciesRepository: countryCurrenciesRepository,
																			 concurrency: concurrency)
  }
}

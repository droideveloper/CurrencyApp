//
//  MockEndpoint.swift
//  CurrencyTests
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import RxSwift

@testable import Currency

class MockEndpoint: Endpoint {
	
	private var mockCountryCurrencies: Dictionary<String, String>
	private var mockRateResponse: RateResponse
	
	init() {
		mockCountryCurrencies = Dictionary<String, String>()
		mockCountryCurrencies["USD"] = "US"
		mockCountryCurrencies["EUR"] = "EU"
		
		var data = Dictionary<String, Double>()
		data["USD"] = 1.0
		data["EUR"] = 1.0
		mockRateResponse = RateResponse(base: "USD", date: nil, error: nil, rates: data)
	}
	
	func rates(_ base: String?) -> Observable<RateResponse> {
		return Observable.of(mockRateResponse)
	}
	
	func countryCurrencies(_ url: String) -> Observable<Dictionary<String, String>> {
		return Observable.of(mockCountryCurrencies)
	}
}

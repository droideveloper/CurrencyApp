//
//  CurrencyToCountryManagerImp.swift
//  Currency
//
//  Created by Fatih Şen on 18.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public class CurrencyToCountryManagerImp: CurrencyToCountryManager {
	
	private let EURO = "EUR"
	private let DOLLAR = "USD"
	
	private let EUROPE = "EU"
	private let USA = "US"
	
	private lazy var cache = {
		return Dictionary<String, String>()
	}()
	
	public var needsPopulateData: Bool {
		get {
			return cache.count == 0
		}
	}
	
	public func populateCache(_ data: Dictionary<String, String>) {
		cache = data
	}
	
	public func countryCodeFor(currencyCode: String) -> String {
		if currencyCode == EURO {
			return EUROPE
		} else if currencyCode == DOLLAR {
			return USA
		} else {
			return (cache.first { key, value in value == currencyCode })?.key ?? .empty
		}
	}
	
	public func clearAll() {
		cache.removeAll(keepingCapacity: true)
	}
}

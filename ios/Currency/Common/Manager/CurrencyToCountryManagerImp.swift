//
//  CurrencyToCountryManagerImp.swift
//  Currency
//
//  Created by Fatih Şen on 18.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public class CurrencyToCountryManagerImp: CurrencyToCountryManager {
	
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
		return (cache.first { key, value in key == currencyCode })?.value ?? .empty
	}
	
	public func clearAll() {
		cache.removeAll(keepingCapacity: true)
	}
}

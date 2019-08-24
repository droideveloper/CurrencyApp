//
//  CurrencyToCountryManager.swift
//  Currency
//
//  Created by Fatih Şen on 18.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public protocol CurrencyToCountryManager {
	
	var needsPopulateData: Bool { get }
	func countryCodeFor(currencyCode: String) -> String
	func populateCache(_ data: Dictionary<String, String>)
	func clearAll()
}

//
//  CurrencyToFlagUrlManagerImp.swift
//  Currency
//
//  Created by Fatih Şen on 18.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public class CurrencyToFlagUrlManagerImp: CurrencyToFlagUrlManager {
	
	private let currencyToCountryManager: CurrencyToCountryManager
	
	public init(_ currencyToCountryManager: CurrencyToCountryManager) {
		self.currencyToCountryManager = currencyToCountryManager
	}
	
	public func countryFlagUrlFor(currencyCode: String) -> String {
		let countryCode = currencyToCountryManager.countryCodeFor(currencyCode: currencyCode)
		if countryCode != .empty {
			// Maybe should have done same with android :)
			return "https://www.countryflags.io/\(countryCode)/flat/64.png"
		}
		return .empty
	}
}

//
//  String+URL.swift
//  Currency
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

extension String {
	
	public func toUrl() -> URL? {
		return URL(string: self)
	}
	
	public func toCurrencyName() -> String? {
		let locale = Locale(identifier: self)
		return locale.localizedString(forCurrencyCode: self)
	}
}

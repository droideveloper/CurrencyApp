//
//  RateResponse.swift
//  Currency
//
//  Created by Fatih Şen on 18.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public struct RateResponse: Codable {
	
	var base: String?
	var date: Date?
	var error: String?
	var rates: Dictionary<String, Double>?
}

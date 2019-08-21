//
//  RateChangeDelegate.swift
//  Currency
//
//  Created by Fatih Şen on 18.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public protocol RateChangeDelegate: class {
	func rateChange(newValue: RateEntity)
}

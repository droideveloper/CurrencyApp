//
//  SelectedCurrencyEvent.swift
//  Currency
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa

public class SelectedCurrencyEvent: Event {
	
	public let rate: RateEntity
	
	public init(rate: RateEntity) {
		self.rate = rate
	}
}

//
//  RateView.swift
//  Currency
//
//  Created by Fatih Şen on 18.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa

public protocol RateView: class, View {
	func render(model: RateModel)
}

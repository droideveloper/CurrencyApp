//
//  EndpointRequestable.swift
//  Currency
//
//  Created by Fatih Şen on 18.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa

enum EndpointRequestable: Requestable {
	case rates(String?)
	case countryCurrencies(String)
	
	var baseUrl: String {
		get {
			return .empty
		}
	}
	
	var request: URLRequest {
		get {
			fatalError("not implemented yet")
		}
	}
}

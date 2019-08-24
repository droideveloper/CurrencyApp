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
			return "https://revolut.duckdns.org/"
		}
	}
	
	var request: URLRequest {
		get {
			switch self {
			case .rates(let base):
				if let base = base {
					return create(url: "\(baseUrl)/latest?base=\(base)", httpMethod: .get)
				}
				return create(url: "\(baseUrl)/latest?base=EUR", httpMethod: .get)
			case .countryCurrencies(let url):
				return create(url: url, httpMethod: .get)
			}
		}
	}
	
	var interceptors: [Interceptor]? {
		get {
			return nil
		}
	}
}

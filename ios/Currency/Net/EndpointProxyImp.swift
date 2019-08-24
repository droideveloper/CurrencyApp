//
//  EndpointProxyImp.swift
//  Currency
//
//  Created by Fatih Şen on 18.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift

public class EndpointProxyImp: EndpointProxy {
	
	private let endpoint: Endpoint
	
	public init(_ endpoint: Endpoint) {
		self.endpoint = endpoint
	}
	
	public func rates(_ base: String?) -> Observable<Resource<Dictionary<String, Double>>> {
		return endpoint.rates(base)
			.applyResource()
	}
	
	public func countryCurrencies(_ url: String) -> Observable<Resource<Dictionary<String, String>>> {
		return endpoint.countryCurrencies(url)
			.applyResource()
	}
}

extension Observable where Element == RateResponse {
	
	public func applyResource() -> Observable<Resource<Dictionary<String, Double>>> {
		return map { response in
			guard let error = response.error else {
				return Resource.success(response.base, response.date, data: response.rates)
			}
			return Resource.failure(error)
		}
	}
}

extension Observable where Element == Dictionary<String, String> {
	
	public func applyResource() -> Observable<Resource<Dictionary<String, String>>> {
		return map { response in
			guard let error = response["error"] else {
				return Resource.success(nil, nil, data: response)
			}
			return Resource.failure(error)
		}
	}
}

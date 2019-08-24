
//
//  EndpointImp.swift
//  Currency
//
//  Created by Fatih Şen on 18.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift
import MVICocoa
import Alamofire

public class EndpointImp: Endpoint {
	
	private let decoder: JSONDecoder
	private let dateFormat: DateFormatter = {
		let formatter = DateFormatter()
		formatter.dateFormat = "yyyy-MM-dd"
		return formatter
	}()
	
	public init() {
		let decoder = JSONDecoder()
		decoder.dateDecodingStrategy = .formatted(dateFormat)
		self.decoder = decoder
	}
	
	public func rates(_ base: String?) -> Observable<RateResponse> {
		let method = EndpointRequestable.rates(base)
		return method.request
			.perform(decoder: decoder)
	}
	
	public func countryCurrencies(_ url: String) -> Observable<Dictionary<String, String>> {
		let method = EndpointRequestable.countryCurrencies(url)
		return method.request
			.perform()
	}
}

extension URLRequest {
	
	func perform<T>() -> Observable<T> where T: Decodable {
		return Alamofire.request(self)
			.serialize()
			.debug(with: self)
	}
	
	func perform<T>(decoder: JSONDecoder) -> Observable<T> where T: Decodable {
		return Alamofire.request(self)
			.serialize(decoder: decoder)
			.debug(with: self)
	}
}

extension Observable: Loggable {
	
	public func debug(with request: URLRequest? = nil) -> Observable<Element> {
		#if DEBUG || MOCK
		if let request = request {
			let requestString = "\(request.httpMethod ?? .empty) --> \(request.description)"
			if let headers = request.allHTTPHeaderFields {
				headers.forEach { key, value in
					log(.debug, "\(key): \(value)")
				}
			}
			log(.debug, requestString)
			if let body = request.httpBody {
				let payload = String(data: body, encoding: .utf8) ?? .empty
				log(.debug, "\(payload)")
			}
		}
		return self.do(onNext: { [weak weakSelf = self] response in
			if let request = request {
				let responseString = "\(request.httpMethod ?? .empty) <-- \(request.description)"
				weakSelf?.log(.debug, responseString)
			}
		})
		#else
		return self
		#endif
	}
}

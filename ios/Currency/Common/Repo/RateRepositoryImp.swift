//
//  RateRepositoryImp.swift
//  Currency
//
//  Created by Fatih Şen on 18.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift

public class RateRepositoryImp: RateRepository {
	
	private let proxy: EndpointProxy
	
	public init(_ proxy: EndpointProxy) {
		self.proxy = proxy
	}
	
	public func rates(_ base: String?) -> Observable<Resource<Dictionary<String, Double>>> {
		return proxy.rates(base)
	}
}

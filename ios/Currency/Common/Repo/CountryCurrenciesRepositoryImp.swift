//
//  CountryCurrenciesRepositoryImp.swift
//  Currency
//
//  Created by Fatih Şen on 18.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift

public class CountryCurrenciesRepositoryImp: CountryCurrenciesRepository {
	
	private let proxy: EndpointProxy
	
	public init(_ proxy: EndpointProxy) {
		self.proxy = proxy
	}
	
	public func countryCurrencies(_ url: String) -> Observable<Resource<Dictionary<String, String>>> {
		return proxy.countryCurrencies(url)
	}
}

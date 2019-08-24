//
//  CountryCurrenciesRepository.swift
//  Currency
//
//  Created by Fatih Şen on 18.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift

public protocol CountryCurrenciesRepository {
	
	func countryCurrencies(_ url: String) -> Observable<Resource<Dictionary<String, String>>>
}

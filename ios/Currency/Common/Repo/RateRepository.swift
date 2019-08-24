//
//  RateRepository.swift
//  Currency
//
//  Created by Fatih Şen on 18.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import RxSwift

public protocol RateRepository {
	
	func rates(_ base: String?) -> Observable<Resource<Dictionary<String, Double>>>
}

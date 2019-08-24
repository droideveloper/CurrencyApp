//
//  RateEntity.swift
//  Currency
//
//  Created by Fatih Şen on 18.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa

public class RateEntity: Codable, Equatable {
	
  public static let empty = RateEntity() // TODO implement empty

	var base: String = .empty
	var amount: Double = 0.0
	
	public init(_ base: String = .empty, _ amount: Double = 0.0) {
		self.base = base
		self.amount = amount
	}
	
	public func copy(_ base: String? = nil, _ amount: Double? = nil) -> RateEntity {
    return RateEntity(base ?? self.base, amount ?? self.amount)
	}
	
  public static func == (lhs: RateEntity, rhs: RateEntity) -> Bool {
    return lhs.base == rhs.base
  }  
}

//
//  RateModel.swift
//  Currency
//
//  Created by Fatih Şen on 18.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa

public struct RateModel: Model {

	public static let empty = RateModel(state: .idle, data: [:])

  public var state: SyncState
  public var data: Dictionary<String, Double>

  public func copy(state: SyncState? = nil, data: Dictionary<String, Double>? = nil) -> RateModel {
    return RateModel(state: state ?? self.state, data: data ?? self.data)
  }
}

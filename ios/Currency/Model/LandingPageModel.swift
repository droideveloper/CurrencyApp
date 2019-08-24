//
//  LandingPageModel.swift
//  Currency
//
//  Created by Fatih Şen on 18.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa

public struct LandingPageModel: Model {
	
	public static let empty = LandingPageModel(state: .idle, data: [:])

  public var state: SyncState
  public var data: Dictionary<String, String>

  public func copy(state: SyncState? = nil, data: Dictionary<String, String>? = nil) -> LandingPageModel {
    return LandingPageModel(state: state ?? self.state, data: data ?? self.data)
  }
}

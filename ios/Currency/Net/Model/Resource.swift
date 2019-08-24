//
//  Resource.swift
//  Currency
//
//  Created by Fatih Şen on 18.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public enum Resource<T> {
	case success(String?, Date?, data: T?)
	case failure(String?)
}

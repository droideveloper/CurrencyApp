//
//  MockApp.swift
//  CurrencyTests
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import Swinject
import MVICocoa

class MockApp: Injectable {
	
	public static let shared = MockApp()
	
	lazy var container: Container = {
		let container = Container()
		
		let unit = UnitTestDependency(container: container)
		unit.setUp()
		
		let ui = UITestDependency(container: container)
		ui.setUp()
		
		return container
	}()
	
	private init() {  }
}


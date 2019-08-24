//
//  MockLandingPageView.swift
//  CurrencyTests
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import RxSwift
import Swinject

@testable import Currency

class MockLadingPageView: BaseMockView<LandingPageModel, LandingPageViewModel>, LandingPageView {
	
	var currencyToCountryManager: CurrencyToCountryManager!
	var block: () -> Void = { }
	
	var showProgress = AtomicProperty<Bool>(defaultValue: false)
	
	override func attach() {
		super.attach()
		
		disposeBag += viewModel.state()
			.map { state in
				switch state {
				case .operation(let type, _): return type == refresh
				default: return false
				}
			}
			.subscribe(onNext: { [unowned self] state in
				self.showProgress.value = state
			})
		
		accept(LoadCountryCurrenciesEvent())
	}
	
	override func render(model: LandingPageModel) {
		switch model.state {
		case .idle: break;
		case .failure(_): break;
		case .operation(_, _):
			render(model.data)
			break;
		}
	}
	
	override func detach() {
		super.detach()
	}
	
	private func render(_ data: Dictionary<String, String>) {
		if !data.isEmpty {
			
			currencyToCountryManager.populateCache(data)
			
			block()
		}
	}
}

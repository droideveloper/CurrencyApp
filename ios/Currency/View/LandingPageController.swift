//
//  LandingPageController.swift
//  Currency
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import RxSwift
import RxCocoa
import Swinject
import SwinjectStoryboard

class LandingPageController: BaseViewController<LandingPageModel, LandingPageViewModel>, LandingPageView {

	@IBOutlet weak var viewProgress: UIActivityIndicatorView!
	
	var currencyToCountryManager: CurrencyToCountryManager!
	
  override func attach() {
    super.attach()
		
		disposeBag += viewModel.state()
			.map { state in
				switch state {
					case .operation(let type, _): return type == refresh
					default: return false
				}
			}
			.subscribe(viewProgress.rx.isAnimating)
		
		checkIfInitialLoadNeeded()
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
	
	private func render(_ data: Dictionary<String, String>) {
		if !data.isEmpty {
			currencyToCountryManager.populateCache(data)
			// ensure we do have it
			guard let container = container else { return }
			
			let storyboard = SwinjectStoryboard.create(name: "Main", bundle: Bundle.main, container: container)
			let rateController = storyboard.instantiateViewController(withIdentifier: String(describing: RateController.self))
			self.present(rateController, animated: true, completion: nil)
		}
	}
	
	private func checkIfInitialLoadNeeded() {
		if currencyToCountryManager.needsPopulateData {
			accept(LoadCountryCurrenciesEvent())
		}
	}
}

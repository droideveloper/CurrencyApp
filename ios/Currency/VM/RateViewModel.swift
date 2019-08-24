//
//  RateViewModel.swift
//  Currency
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import RxSwift

class RateViewModel: BaseViewModel<RateModel> {
  
  private weak var view: RateView?

	init(view: RateView, concurrency: Concurrency) {
		super.init(concurrency: concurrency)
    self.view = view
  }

  override func attach() {
    super.attach()
		guard let view = view else { return }
    // convert view events into relative intent and pass them pipeline
    disposeBag += view.viewEvents()
      .toIntent(view.container)
      .subscribe(onNext: accept(_ :))
  }
}

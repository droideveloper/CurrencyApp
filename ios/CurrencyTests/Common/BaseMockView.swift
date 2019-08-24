//
//  BaseMockView.swift
//  CurrencyTests
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import RxSwift
import RxCocoa
import Swinject

open class BaseMockView<T: Model, V: ViewModel>: View where V.Model == T {
		
	public var container: Container? {
		get {
			let shared = MockApp.shared
			return shared.container
		}
	}
	
	var viewModel: V!
	
	private let events = PublishRelay<MVICocoa.Event>()
	public let disposeBag = CompositeDisposeBag()
	
	open func setUp() { /*no opt*/ }
	
	open func attach() {
		// base attach functionality
		viewModel.attach()
		
		// will render view state
		disposeBag += viewModel.store()
			.subscribe(onNext: render(model:))
	}
	
	open func render(model: T) { /*no opt*/ }
	
	open func detach() {
		disposeBag.clear()
		viewModel.detach()
	}
	
	open func viewEvents() -> Observable<MVICocoa.Event> {
		return events.share()
	}
	
	public func accept(_ event: MVICocoa.Event) {
		events.accept(event)
	}
}


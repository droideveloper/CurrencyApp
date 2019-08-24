//
//  LoadRateIntent.swift
//  Currency
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import RxSwift

class LoadRateIntent: ObservableIntent<RateModel> {
	
	private let concurrency: Concurrency
	
	private let rateRepository: RateRepository
	private let base: String?
	
	public init(base: String?, rateRepository: RateRepository, concurrency: Concurrency) {
		self.base = base
		self.rateRepository = rateRepository
		self.concurrency = concurrency
	}
	
  override func invoke() -> Observable<Reducer<RateModel>> {
    return rateRepository.rates(base)
      .concatMap(success(_ :))
      .catchError(failure(_ :))
      .startWith(initial())
      .subscribeOn(concurrency.dispatchScheduler)
  }

  private func initial() -> Reducer<RateModel> {
		return { o in o.copy(state: .operationOf(refresh), data: [:]) }
  }

  private func success(_ resource: Resource<Dictionary<String, Double>>) -> Observable<Reducer<RateModel>> {
    switch resource {
      case .success(_, _, let data): return Observable.of(
				{ o in o.copy(state: .operationOf(refresh), data: data ?? [:]) },
				{ o in o.copy(state: .idle, data: [:]) })
      case .failure(let messageFriendly): return Observable.of(
        { o in o.copy(state: .failure(NSError(domain: messageFriendly ?? .empty, code: -1, userInfo: nil))) },
        { o in o.copy(state: .idle) })  
    }
  }

  private func failure(_ error: Error) -> Observable<Reducer<RateModel>> {
    return Observable.of(
      { o in o.copy(state: .failure(error)) },
      { o in o.copy(state: .idle) })
  }
}

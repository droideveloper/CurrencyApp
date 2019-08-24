//
//  LoadCountryCurrenciesIntent.swift
//  Currency
//
//  Created by Fatih Şen on 24.08.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation
import MVICocoa
import RxSwift

class LoadCountryCurrenciesIntent: ObservableIntent<LandingPageModel> {

	private let countryCurrenciesRepository: CountryCurrenciesRepository
	private let concurrency: Concurrency
	
	private let url: String
	
	init(url: String, countryCurrenciesRepository: CountryCurrenciesRepository, concurrency: Concurrency) {
		self.url = url
		self.concurrency = concurrency
		self.countryCurrenciesRepository = countryCurrenciesRepository
	}
	
  override func invoke() -> Observable<Reducer<LandingPageModel>> {
    return countryCurrenciesRepository.countryCurrencies(url) 
      .concatMap(success(_ :))
      .catchError(failure(_ :))
      .startWith(initial())
      .subscribeOn(concurrency.dispatchScheduler)
  }

  private func initial() -> Reducer<LandingPageModel> {
		return { o in o.copy(state: .operationOf(refresh), data: [:]) }
  }

  private func success(_ resource: Resource<Dictionary<String, String>>) -> Observable<Reducer<LandingPageModel>> {
    switch resource {
      case .success(_, _, let data): return Observable.of(
				{ o in o.copy(state: .operationOf(refresh), data: data ?? [:]) },
				{ o in o.copy(state: .idle, data: [:]) })
      case .failure( let messageFriendly): return Observable.of(
        { o in o.copy(state: .failure(NSError(domain: messageFriendly ?? .empty, code: -1, userInfo: nil))) },
        { o in o.copy(state: .idle) })  
    }
  }

  private func failure(_ error: Error) -> Observable<Reducer<LandingPageModel>> {
    return Observable.of(
      { o in o.copy(state: .failure(error)) },
      { o in o.copy(state: .idle) })
  }
}

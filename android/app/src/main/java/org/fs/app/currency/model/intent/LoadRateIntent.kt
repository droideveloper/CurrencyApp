/*
 *  Copyright (C) 2019 Fatih, Currency Android Kotlin.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.fs.app.currency.model.intent

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.fs.app.currency.common.repo.RatesRepository
import org.fs.app.currency.model.RateModel
import org.fs.app.currency.net.model.Resource
import org.fs.app.currency.util.Operations.Companion.REFRESH
import org.fs.architecture.mvi.common.*
import java.io.IOException

class LoadRateIntent(
  private val base: String,
  private val ratesRepository: RatesRepository): ObservableIntent<RateModel>() {

  override fun invoke(): Observable<Reducer<RateModel>> = ratesRepository.rates(base)
    .concatMap(::success)
    .onErrorResumeNext(::failure)
    .startWith(initial())
    .subscribeOn(Schedulers.io())

  private fun success(resource: Resource<Map<String, Double>>): Observable<Reducer<RateModel>> = when(resource) {
    is Resource.Success<Map<String, Double>> -> Observable.just(
      { o -> o.copy(state = Operation(REFRESH), data = resource.data ?: emptyMap()) },
      { o -> o.copy(state = Idle, data = emptyMap()) })
    is Resource.Failure<Map<String, Double>> -> Observable.just(
      { o -> o.copy(state = Failure(IOException(resource.error))) },
      { o -> o.copy(state = Idle) })
  }

  private fun failure(error: Throwable): Observable<Reducer<RateModel>> = Observable.just(
    { o -> o.copy(state = Failure(error)) },
    { o -> o.copy(state = Idle) })

  private fun initial(): Reducer<RateModel> = { o -> o.copy(state = Operation(REFRESH), data = emptyMap()) }
}
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
package org.fs.app.currency.wm

import org.fs.app.currency.common.repo.RatesRepository
import org.fs.app.currency.model.RateModel
import org.fs.app.currency.model.event.LoadRatesEvent
import org.fs.app.currency.model.intent.LoadRateIntent
import org.fs.app.currency.model.intent.NothingIntent
import org.fs.app.currency.view.RateActivityView
import org.fs.architecture.mvi.common.Event
import org.fs.architecture.mvi.common.ForActivity
import org.fs.architecture.mvi.common.Idle
import org.fs.architecture.mvi.common.Intent
import org.fs.architecture.mvi.core.AbstractViewModel
import javax.inject.Inject

@ForActivity
class RateActivityViewModel @Inject constructor(
  private val ratesRepository: RatesRepository,
  view: RateActivityView): AbstractViewModel<RateModel, RateActivityView>(view) {

  override fun initState(): RateModel = RateModel(state = Idle, data = emptyMap())

  override fun toIntent(event: Event): Intent = when (event) {
    is LoadRatesEvent -> LoadRateIntent(event.base, ratesRepository)
    else -> NothingIntent<RateModel>() // if we can not resolve event to intent
  }
} 
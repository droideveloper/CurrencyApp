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

import org.fs.app.currency.common.repo.CountryCurrenciesRepository
import org.fs.app.currency.model.LandingPageModel
import org.fs.app.currency.model.event.LoadCountryCurrenciesEvent
import org.fs.app.currency.model.intent.LoadCountryCurrenciesIntent
import org.fs.app.currency.model.intent.NothingIntent
import org.fs.app.currency.view.LandingPageActivityView
import org.fs.architecture.mvi.common.Event
import org.fs.architecture.mvi.common.ForActivity
import org.fs.architecture.mvi.common.Idle
import org.fs.architecture.mvi.common.Intent
import org.fs.architecture.mvi.core.AbstractViewModel
import javax.inject.Inject

@ForActivity
class LandingPageActivityViewModel @Inject constructor(
  private val countryCurrenciesRepository: CountryCurrenciesRepository,
  view: LandingPageActivityView): AbstractViewModel<LandingPageModel, LandingPageActivityView>(view) {

  override fun initState(): LandingPageModel = LandingPageModel(state = Idle, data = emptyMap())

  override fun toIntent(event: Event): Intent = when (event) {
    is LoadCountryCurrenciesEvent -> LoadCountryCurrenciesIntent(countryCurrenciesRepository)
    else -> NothingIntent<LandingPageModel>() // if we can not resolve event to intent
  }
} 
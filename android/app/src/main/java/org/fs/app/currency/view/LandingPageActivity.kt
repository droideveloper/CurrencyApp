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
package org.fs.app.currency.view

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.view_landing_page_activity.*
import org.fs.app.currency.R
import org.fs.app.currency.common.manager.CurrencyToCountryManager
import org.fs.app.currency.model.LandingPageModel
import org.fs.app.currency.model.event.LoadCountryCurrenciesEvent
import org.fs.app.currency.util.Operations.Companion.REFRESH
import org.fs.app.currency.util.showProgress
import org.fs.app.currency.wm.LandingPageActivityViewModel
import org.fs.architecture.mvi.common.Failure
import org.fs.architecture.mvi.common.Idle
import org.fs.architecture.mvi.common.Operation
import org.fs.architecture.mvi.core.AbstractActivity
import org.fs.architecture.mvi.util.plusAssign
import javax.inject.Inject

class LandingPageActivity : AbstractActivity<LandingPageModel, LandingPageActivityViewModel>(),
  LandingPageActivityView {

  @Inject lateinit var currencyToCountryManager: CurrencyToCountryManager

  override val layoutRes: Int get() = R.layout.view_landing_page_activity

  override fun setUp(state: Bundle?) {
    viewProgress.showProgress(false)
  }

  override fun attach() {
    super.attach()

    disposeBag += viewModel.state()
      .map { state ->
        if (state is Operation ) {
          return@map state.type == REFRESH
        }
        return@map false
      }
      .subscribe(viewProgress::showProgress)

    disposeBag += viewModel.storage()
      .subscribe(::render)

    checkIfInitialLoadNeeded()
  }

  override fun render(model: LandingPageModel) = when(model.state) {
    is Idle -> Unit
    is Failure -> Unit
    is Operation -> when(model.state.type) {
      REFRESH -> render(model.data)
      else -> Unit
    }
  }

  private fun render(data: Map<String, String>) {
    if (data.isNotEmpty()) {
      currencyToCountryManager.populateCache(data)
      // then start activity
      startActivity(Intent(this, RateActivity::class.java))
      finish()
    }
  }

  private fun checkIfInitialLoadNeeded() {
    if (currencyToCountryManager.needsPopulateData) {
      accept(LoadCountryCurrenciesEvent())
    }
  }
} 
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

package org.fs.app.currency.mock

import org.fs.app.currency.common.base.AbstractView
import org.fs.app.currency.common.dagger.TestInjection
import org.fs.app.currency.common.manager.RateManager
import org.fs.app.currency.model.RateModel
import org.fs.app.currency.model.event.LoadRatesEvent
import org.fs.app.currency.util.C.Companion.EUROPE_CURRENCY
import org.fs.app.currency.util.Operations.Companion.REFRESH
import org.fs.app.currency.view.RateActivityView
import org.fs.app.currency.wm.RateActivityViewModel
import org.fs.architecture.mvi.common.Failure
import org.fs.architecture.mvi.common.ForActivity
import org.fs.architecture.mvi.common.Idle
import org.fs.architecture.mvi.common.Operation
import org.fs.architecture.mvi.util.plusAssign
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

@ForActivity
class RateActivity: AbstractView<RateModel, RateActivityViewModel>(), RateActivityView {

  @Inject lateinit var rateManager: RateManager

  var block: () -> Unit = { }

  val showingProgress by lazy { AtomicBoolean(false) }

  override fun create() {
    TestInjection.inject(this)
  }

  override fun attach() {
    viewModel.attach()

    disposeBag += viewModel.state()
      .map { state ->
        if (state is Operation) {
          return@map state.type == REFRESH
        }
        return@map false
      }
      .subscribe(showingProgress::set)

    disposeBag += viewModel.storage()
      .subscribe(::render)

    viewEvents.accept(LoadRatesEvent(EUROPE_CURRENCY))
  }

  override fun detach() = viewModel.detach().also {
    disposeBag.clear()
  }

  override fun render(model: RateModel) = when(model.state) {
    is Idle -> Unit
    is Failure -> Unit
    is Operation -> {
      val state = model.state as Operation
      when(state.type) {
        REFRESH -> render(model.data)
        else -> Unit
      }
    }
  }

  private fun render(data: Map<String, Double>) {
    if (data.isNotEmpty()) {
      rateManager.rates = data
      block()
    }
  }
}
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

import android.os.Bundle
import android.text.TextUtils
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.Observable
import kotlinx.android.synthetic.main.view_rates_activity.*
import org.fs.app.currency.R
import org.fs.app.currency.common.manager.RateManager
import org.fs.app.currency.common.manager.delegate.OnRateChangeListener
import org.fs.app.currency.model.entity.RateEntity
import org.fs.app.currency.model.RateModel
import org.fs.app.currency.model.event.LoadRatesEvent
import org.fs.app.currency.util.C.Companion.RECYCLER_CACHE_SIZE
import org.fs.app.currency.util.Operations.Companion.REFRESH
import org.fs.app.currency.util.applyDivider
import org.fs.app.currency.util.showProgress
import org.fs.app.currency.view.adapter.RateAdapter
import org.fs.app.currency.wm.RateActivityViewModel
import org.fs.architecture.mvi.common.Failure
import org.fs.architecture.mvi.common.Idle
import org.fs.architecture.mvi.common.Operation
import org.fs.architecture.mvi.core.AbstractActivity
import org.fs.architecture.mvi.util.ObservableList
import org.fs.architecture.mvi.util.plusAssign
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RateActivity : AbstractActivity<RateModel, RateActivityViewModel>(), RateActivityView, OnRateChangeListener {

  @Inject lateinit var rateAdapter: RateAdapter
  @Inject lateinit var dataSet: ObservableList<RateEntity>

  @Inject lateinit var rateManager: RateManager

  override val layoutRes: Int get() = R.layout.view_rates_activity

  private val verticalDivider by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_vertical_rate_divider, theme) }

  override fun setUp(state: Bundle?) {
    viewRecycler.apply {
      setItemViewCacheSize(RECYCLER_CACHE_SIZE)
      layoutManager = LinearLayoutManager(context)
      adapter = rateAdapter
      verticalDivider?.applyDivider(this)
    }
    viewSwipeRefreshLayout.isEnabled = false
  }

  override fun attach() {
    super.attach()

    rateManager.addOnRateChangedListener(this)

    disposeBag += viewModel.state()
      .map { state ->
        if (state is Operation) {
          return@map state.type == REFRESH && dataSet.isEmpty()
        }
        return@map false
      }
      .subscribe(viewSwipeRefreshLayout::showProgress)

    disposeBag += viewModel.storage()
      .subscribe(::render)

    disposeBag += Observable.interval(0, 1, TimeUnit.SECONDS)
      .subscribe(::periodicallyCheckNewData)
  }

  override fun detach() {
    super.detach().also {
      rateManager.removeOnRateChangedListener(this)
    }
  }

  override fun render(model: RateModel) = when(model.state) {
    is Idle -> Unit
    is Failure -> Unit
    is Operation -> when(model.state.type) {
      REFRESH -> render(model.data)
      else -> Unit
    }
  }

  override fun onRateChange(newValue: RateEntity) {
    if (newValue != RateEntity.EMPTY) {
      val base = newValue.base
      val amount = newValue.amount
      // search for index
      val position = dataSet.indexOfFirst { r -> TextUtils.equals(r.base, base) }
      if (position != -1 && position != 0) {
        // scroll to position
        viewRecycler.scrollToPosition(0)
        // then change values
        val currentValue = dataSet.removeAt(position)
        currentValue.amount = amount // just ensure we get latest value from data
        dataSet.add(0, currentValue)
      }
    }
  }

  private fun render(data: Map<String, Double>) {
    if (data.isNotEmpty()) {
      val currentRate = rateManager.rate
      val currentRateCurrency = currentRate.base
      // update current item ratio as 1
      val hashMap = HashMap<String, Double>()
      hashMap[currentRateCurrency] = 1.0
      hashMap.putAll(data)
      // notify delegates
      rateManager.rates = hashMap
      // if our dataSet is empty, we want to bind it in our adapter
      if (dataSet.isEmpty()) {
        // if initial load we grab items
        val d = data.keys.map { base ->
          val r = RateEntity()
          r.base = base
          return@map r
        }
        dataSet.add(rateManager.rate) // set first one selected
        dataSet.addAll(d)
      }
    }
  }

  private fun periodicallyCheckNewData(times: Long) = accept(LoadRatesEvent(rateManager.rate.base))
} 
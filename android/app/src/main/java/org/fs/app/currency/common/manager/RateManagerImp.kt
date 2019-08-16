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

package org.fs.app.currency.common.manager

import org.fs.app.currency.common.manager.delegate.OnRateChangeListener
import org.fs.app.currency.common.manager.delegate.OnRatesChangeListener
import org.fs.app.currency.model.RateEntity
import org.fs.app.currency.util.C.Companion.EUROPE_CURRENCY
import java.text.DecimalFormat
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToLong

@Singleton
class RateManagerImp @Inject constructor(): RateManager {

  private val ratesChangeCallbacks by lazy { ArrayList<OnRatesChangeListener>() }
  private val rateChangeCallbacks by lazy { ArrayList<OnRateChangeListener>() }
  private val amountFormat by lazy { DecimalFormat("#.##") }

  override var rate: RateEntity = RateEntity().apply {
    base = EUROPE_CURRENCY
    amount = 0.0
  }
    set(value) {
      // we want to be notified all the time
      field = value
      notifyRateChange()
    }

  override var rates: Map<String, Double> = emptyMap()
    set(value) {
      field = value
      notifyRatesChange()
    }

  override fun amount(): Double = rate.amount

  override fun amountForCurrency(currencyCode: String): Double {
    val ratio = rates[currencyCode] ?: 0.0
    return (ratio * rate.amount * 100.0).roundToLong() / 100.0
  }

  override fun parse(text: String): Double {
    val number = amountFormat.parse(text)
    if (number != null) {
      return (number.toDouble() * 100.0).roundToLong() / 100.0
    }
    return 0.0
  }

  override fun format(amount: Double): String = amountFormat.format((amount * 100.0).roundToLong() / 100.0)

  override fun addOnRateChangedListener(callback: OnRateChangeListener) {
    synchronized(this) {
      rateChangeCallbacks.add(callback)
    }
  }

  override fun removeOnRateChangedListener(callback: OnRateChangeListener) {
    synchronized(this) {
      rateChangeCallbacks.remove(callback)
    }
  }

  override fun addOnRatesChangedListener(callback: OnRatesChangeListener) {
    synchronized(this) {
      ratesChangeCallbacks.add(callback)
    }
  }

  override fun removeOnRatesChangedListener(callback: OnRatesChangeListener) {
    synchronized(this) {
      ratesChangeCallbacks.remove(callback)
    }
  }


  private fun notifyRateChange() {
    (0 until rateChangeCallbacks.size).forEach { index ->
      val callback = rateChangeCallbacks[index]
      callback.onRateChange(rate)
    }
  }

  private fun notifyRatesChange() {
    (0 until ratesChangeCallbacks.size).forEach { index ->
      val callback = ratesChangeCallbacks[index]
      callback.onRatesChange(rates)
    }
  }
}
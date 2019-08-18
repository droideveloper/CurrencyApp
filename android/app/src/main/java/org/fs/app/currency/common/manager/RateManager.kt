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

interface RateManager {

  var rate: RateEntity

  var rates: Map<String, Double>

  fun amount(): Double
  fun parse(text: String): Double
  fun format(amount: Double): String

  fun amountForCurrency(currencyCode: String): Double

  fun addOnRateChangedListener(callback: OnRateChangeListener)
  fun removeOnRateChangedListener(callback: OnRateChangeListener)

  fun addOnRatesChangedListener(callback: OnRatesChangeListener)
  fun removeOnRatesChangedListener(callback: OnRatesChangeListener)
}
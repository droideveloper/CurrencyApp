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

import androidx.collection.ArrayMap
import org.fs.app.currency.util.C.Companion.EUROPE_COUNTRY
import org.fs.app.currency.util.C.Companion.EUROPE_CURRENCY
import org.fs.app.currency.util.C.Companion.USA_COUNTRY
import org.fs.app.currency.util.C.Companion.USA_CURRENCY
import org.fs.architecture.mvi.util.EMPTY
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyToCountryManagerImp @Inject constructor(): CurrencyToCountryManager {

  companion object {
    private const val DEFAULT_SIZE = 1
  }

  private val countryToCurrencyCache by lazy { ArrayMap<String, String>() }

  override val needsPopulateData: Boolean get() = countryToCurrencyCache.size <= DEFAULT_SIZE

  override fun countryCodeForCurrency(currencyCode: String): String {
    for (entry in countryToCurrencyCache) {
      if (USA_CURRENCY == currencyCode) {
        return USA_COUNTRY
      }
      if (entry.value == currencyCode) {
        return entry.key
      }
    }
    return String.EMPTY
  }

  override fun populateCache(map: Map<String, String>) {
    val filtered = map.filter { entry -> entry.value != EUROPE_CURRENCY }
    countryToCurrencyCache.putAll(filtered) // will what we need
    countryToCurrencyCache[EUROPE_COUNTRY] = EUROPE_CURRENCY // append default
  }

  override fun clearAll() = countryToCurrencyCache.clear()
}
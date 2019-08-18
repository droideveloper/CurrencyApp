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

import org.fs.app.currency.BuildConfig.BASE_FLAG_AUTHORITY
import org.fs.app.currency.BuildConfig.BASE_FLAG_SCHEME
import org.fs.app.currency.util.C.Companion.PATH_FLAG_SIZE
import org.fs.app.currency.util.C.Companion.PATH_FLAG_TYPE
import org.fs.architecture.mvi.util.EMPTY
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyToFlagUrlManagerImp @Inject constructor(private val currencyToCountryManager: CurrencyToCountryManager): CurrencyToFlagUrlManager {

  override fun countryFlagUrlFor(currencyCode: String): String {
    val countryCode = currencyToCountryManager.countryCodeForCurrency(currencyCode)
    if (countryCode != String.EMPTY) {
      val buffer = StringBuffer()
      buffer.append("$BASE_FLAG_SCHEME://")
      buffer.append(BASE_FLAG_AUTHORITY)
      buffer.append("/$countryCode")
      buffer.append("/$PATH_FLAG_TYPE")
      buffer.append("/$PATH_FLAG_SIZE")
      return buffer.toString()
    }
    return String.EMPTY
  }
}
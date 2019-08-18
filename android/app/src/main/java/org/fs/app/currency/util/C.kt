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

package org.fs.app.currency.util

sealed class C {

  companion object {

    const val RECYCLER_CACHE_SIZE = 10

    // network
    const val CACHE_DIR = "caches"
    const val CACHE_SIZE = 24 * 1024 * 1024L
    const val DEFAULT_TIMEOUT = 20L

    // currency endpoint methods
    const val GET_LATEST = "/latest"

    // moshi
    const val DATE_FORMAT_STR = "yyyy-MM-dd"

    // currency codes
    const val EUROPE_CURRENCY = "EUR"
    const val EUROPE_COUNTRY  = "EU" // All Europe considered as one country
    const val USA_CURRENCY = "USD" // there are other countries uses this so we only check in for The American Money
    const val USA_COUNTRY = "US"

    // flag url components
    const val PATH_FLAG_TYPE = "flat"
    const val PATH_FLAG_SIZE = "64.png"
  }
}
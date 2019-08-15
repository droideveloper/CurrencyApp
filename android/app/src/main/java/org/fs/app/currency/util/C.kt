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

    // endpoint methods
    const val GET_LATEST = "latest"

    // moshi
    const val DATE_FORMAT_STR = "yyyy-MM-dd"
  }
}
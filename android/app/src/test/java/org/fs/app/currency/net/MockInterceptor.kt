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

package org.fs.app.currency.net

import okhttp3.Interceptor
import okhttp3.Response
import org.fs.app.currency.BuildConfig
import org.fs.app.currency.net.response.MockResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockInterceptor @Inject constructor(): Interceptor {

  companion object {
    const val COUNTRY_CURRENCY_URL = 0x01
    const val CURRENCY_RATE_URL = 0x02

    const val ERROR_RESPONSE = "{\"error\": \"invalid request\"}"
  }

  private val cache by lazy { HashMap<Int, Interceptor>().apply {
      put(COUNTRY_CURRENCY_URL, MockCountryCurrenciesInterceptor())
      put(CURRENCY_RATE_URL, MockCurrencyRatesInterceptor())
    }
  }

  override fun intercept(chain: Interceptor.Chain): Response {
    // get uri from request
    val url = chain.request().url().toString()
    val interceptor = when {
      url.startsWith(BuildConfig.COUNTRY_CURRENCY_URL) || url.startsWith("https://example.org/") -> cache[COUNTRY_CURRENCY_URL]
      url.startsWith(BuildConfig.BASE_URL) -> cache[CURRENCY_RATE_URL]
      else -> null
    }

    // fail or use mock
    return interceptor?.intercept(chain) ?: MockResponse.failure(chain.request(), ERROR_RESPONSE)
  }
}
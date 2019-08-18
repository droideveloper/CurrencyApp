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
import org.fs.app.currency.net.MockInterceptor.Companion.ERROR_RESPONSE
import org.fs.app.currency.net.response.MockResponse

class MockCurrencyRatesInterceptor: Interceptor {

  companion object {
    private const val DATA = "{\"base\":\"EUR\",\"date\":\"2018-09-06\",\"rates\":{\"AUD\":1.6126,\"BGN\":1.9512,\"BRL\":4.7804,\"CAD\":1.5302,\"CHF\":1.1248,\"CNY\":7.9262,\"CZK\":25.654,\"DKK\":7.439,\"GBP\":0.89611,\"HKD\":9.1107,\"HRK\":7.4164,\"HUF\":325.71,\"IDR\":17282.0,\"ILS\":4.1607,\"INR\":83.519,\"ISK\":127.5,\"JPY\":129.24,\"KRW\":1301.7,\"MXN\":22.312,\"MYR\":4.8006,\"NOK\":9.7528,\"NZD\":1.7591,\"PHP\":62.443,\"PLN\":4.308,\"RON\":4.6275,\"RUB\":79.386,\"SEK\":10.566,\"SGD\":1.5962,\"THB\":38.039,\"TRY\":7.6101,\"USD\":1.1606,\"ZAR\":17.781}}"
  }

  override fun intercept(chain: Interceptor.Chain): Response {
    // if we send something we do not know we will be exit
    val latest = chain.request().url().queryParameter("base")
    if (latest?.length ?: 0 > 3) {
      return MockResponse.failure(chain.request(), ERROR_RESPONSE)
    }
    return MockResponse.success(chain.request(), DATA)
  }
}
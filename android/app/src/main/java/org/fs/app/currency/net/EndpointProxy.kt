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

import io.reactivex.Observable
import org.fs.app.currency.BuildConfig
import org.fs.app.currency.net.model.Resource

interface EndpointProxy {

  fun rates(base: String): Observable<Resource<Map<String, Double>>>

  fun countryCurrencies(url: String = BuildConfig.COUNTRY_CURRENCY_URL): Observable<Resource<Map<String, String>>>
}
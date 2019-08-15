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
import org.fs.app.currency.net.model.RateResponse
import org.fs.app.currency.net.model.Resource
import retrofit2.Response
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EndpointProxyImp @Inject constructor(private val endpoint: Endpoint): EndpointProxy {

  override fun rates(base: String?): Observable<Resource<Map<String, Double>>> = endpoint.rates(base).toResource()

  private fun Observable<Response<RateResponse>>.toResource(): Observable<Resource<Map<String, Double>>>  = map { response ->
    if (response.isSuccessful) {
      val body = response.body() ?: throw IllegalArgumentException("http error ${response.code()}")
      if (body.error != null) {
        return@map Resource.Failure<Map<String, Double>>(body.error)
      }
      return@map Resource.Success(body.base, body.date, body.data)
    }
    throw IllegalArgumentException("http error ${response.code()}")
  }
}
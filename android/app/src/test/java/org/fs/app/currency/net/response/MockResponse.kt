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

package org.fs.app.currency.net.response

import okhttp3.*
import org.fs.architecture.mvi.util.EMPTY


sealed class MockResponse {

  companion object {
    private const val HEADER_CONTENT_TYPE = "Content-Type"
    private const val VALUE_CONTENT_TYPE = "application/json"

    private const val JSON_MIME = "application/json; charset=utf-8"

    private const val SUCCESS_CODE = 200
    private const val ERROR_CODE = 401

    @JvmStatic fun success(request: Request, data: String): Response = internal(request, SUCCESS_CODE, data)

    @JvmStatic fun failure(request: Request, data: String): Response = internal(request, ERROR_CODE, data)

    @JvmStatic private fun internal(request: Request, code: Int, data: String): Response {
      val body = ResponseBody.create(MediaType.parse(JSON_MIME), data)
      return Response.Builder()
        .addHeader(HEADER_CONTENT_TYPE, VALUE_CONTENT_TYPE)
        .protocol(Protocol.HTTP_1_1) // for some reason can not be null
        .message(String.EMPTY) // for some reason can not be null
        .code(code)
        .body(body)
        .request(request)
        .build()
    }
  }
}
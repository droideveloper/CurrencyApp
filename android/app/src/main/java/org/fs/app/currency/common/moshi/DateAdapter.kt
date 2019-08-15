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

package org.fs.app.currency.common.moshi

import com.squareup.moshi.*
import org.fs.app.currency.BuildConfig
import org.fs.app.currency.util.C.Companion.DATE_FORMAT_STR
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter: JsonAdapter<Date>() {

  private val dateFormat by lazy { SimpleDateFormat(DATE_FORMAT_STR, Locale.getDefault()) }

  @FromJson
  override fun fromJson(reader: JsonReader): Date? {
    val string = reader.nextString()
    try {
      return dateFormat.parse(string)
    } catch (error: Throwable) {
      if (BuildConfig.DEBUG) {
        error.printStackTrace()
      }
    }
    return null
  }

  @ToJson
  override fun toJson(writer: JsonWriter, value: Date?) {
    value?.let { v ->
      val string = dateFormat.format(v)
      writer.value(string)
    }
  }
}
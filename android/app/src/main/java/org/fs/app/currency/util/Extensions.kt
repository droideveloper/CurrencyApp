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

import android.util.Log
import org.fs.app.currency.BuildConfig
import java.io.PrintWriter
import java.io.StringWriter

// Log Helper - Starts

inline fun <reified T> T.isLogEnabled(): Boolean = BuildConfig.DEBUG // only debug mode builds will print logs in logCat
inline fun <reified T> T.getClassTag(): String = T::class.java.simpleName

inline fun <reified T> T.log(error: Throwable) {
  val sw = StringWriter()
  val pw = PrintWriter(sw)
  error.printStackTrace(pw)
  log(Log.ERROR, sw.toString())
}
inline fun <reified T> T.log(message: String) = log(Log.DEBUG, message)

inline fun <reified T> T.log(logLevel: Int, message: String) {
  if (isLogEnabled()) {
    Log.println(logLevel, getClassTag(), message)
  }
}

// Log Helper - Ends
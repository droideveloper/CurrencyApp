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

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.BaseRequestOptions
import kotlinx.android.synthetic.main.view_landing_page_activity.view.*
import org.fs.app.currency.BuildConfig
import org.fs.app.currency.common.glide.CircleImageViewTarget
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


// Glide Base - Starts

fun BaseRequestOptions<*>.applyBase(@DrawableRes placeholder: Int, @DrawableRes errorPlaceHolder: Int): BaseRequestOptions<*> = placeholder(placeholder)
  .error(errorPlaceHolder)
  .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
  .dontAnimate()

// Glide Base - Ends

// UI Helpers - Start

fun ProgressBar.showProgress(showProgress: Boolean) {
  viewProgress.isIndeterminate = showProgress
  viewProgress.visibility = if (showProgress) View.VISIBLE else View.GONE
}

fun SwipeRefreshLayout.showProgress(showProgress: Boolean) {
  isRefreshing = showProgress
}

fun Drawable.applyDivider(viewRecycler: RecyclerView, orientation: Int = DividerItemDecoration.VERTICAL) {
  val divider = DividerItemDecoration(viewRecycler.context, orientation)
  divider.setDrawable(this)
  viewRecycler.addItemDecoration(divider)
}

fun ImageView.toCircleDrawableTarget(): CircleImageViewTarget = CircleImageViewTarget(this)

// UI Helpers - End
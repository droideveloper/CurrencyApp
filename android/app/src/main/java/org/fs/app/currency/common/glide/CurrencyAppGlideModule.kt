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

package org.fs.app.currency.common.glide

import android.content.Context
import android.util.Log
import com.bumptech.glide.BuildConfig
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.fs.app.currency.util.log
import java.io.InputStream
import kotlin.math.roundToInt

@GlideModule class CurrencyAppGlideModule: AppGlideModule() {

  private val logger by lazy { HttpLoggingInterceptor { msg -> log(Log.VERBOSE, msg) }.apply {
      level = HttpLoggingInterceptor.Level.HEADERS
    }
  }

  private val factory by lazy {
    val builder = OkHttpClient.Builder()
    // add logger if debug
    if (BuildConfig.DEBUG) {
      builder.addInterceptor(logger)
    }
    // return http
    builder.build()
  }

  override fun applyOptions(context: Context, builder: GlideBuilder) {
    val calculator = MemorySizeCalculator.Builder(context)
      .setBitmapPoolScreens(3f)
      .build()


    val memoryCacheSize = (calculator.memoryCacheSize * 0.6f).roundToInt() * 1L
    val diskCacheSize = 1024 * 1024 * 128L // cache 128 mb in disk
    // we set up glide this way
    builder.setMemoryCache(LruResourceCache(memoryCacheSize))
    builder.setBitmapPool(LruBitmapPool(calculator.bitmapPoolSize.toLong()))
    builder.setDiskCache(DiskLruCacheFactory(Glide.getPhotoCacheDir(context)?.absolutePath, diskCacheSize))
  }

  override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
    registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(factory))
  }

  override fun isManifestParsingEnabled(): Boolean = false
}
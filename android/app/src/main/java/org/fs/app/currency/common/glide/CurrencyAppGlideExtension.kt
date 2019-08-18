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

import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.request.BaseRequestOptions
import org.fs.app.currency.R
import org.fs.app.currency.util.applyBase

@GlideExtension sealed class CurrencyAppGlideExtension {

  companion object {

    @JvmStatic @GlideOption fun applyCircularCrop(options: BaseRequestOptions<*>): BaseRequestOptions<*> {
      return options.applyBase(R.drawable.ic_place_holder_oval, R.drawable.ic_error_place_holder_oval)
    }
  }
}
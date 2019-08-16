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

import android.graphics.*
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.bumptech.glide.request.target.BitmapImageViewTarget
import org.fs.app.currency.R
import kotlin.math.roundToInt

class CircleImageViewTarget(view: ImageView): BitmapImageViewTarget(view) {

  // target height
  private val th by lazy { view.resources.getDimensionPixelSize(R.dimen.dp56) }

  override fun setResource(resource: Bitmap?) {
    resource?.let { bmp ->
      val h = bmp.height
      val dy = h / 4
      val w = bmp.width
      val temp = Bitmap.createBitmap(bmp, 0, dy, w, h - 2 * dy)

      val scale = th / temp.height.toFloat()
      val sw = (temp.width * scale).roundToInt()
      val sh = (temp.height * scale).roundToInt()

      val scaled = Bitmap.createScaledBitmap(temp, sw, sh, false)
      temp.recycle()

      val left = (scaled.width - scaled.height) / 2
      val height = scaled.height
      val scaledTemp = Bitmap.createBitmap(scaled, left, 0, height, height)
      scaled.recycle()

      val res = view.resources
      val circle = RoundedBitmapDrawableFactory.create(res, scaledTemp)
      circle.isCircular = true

      view.setImageDrawable(circle)
    }
  }
}
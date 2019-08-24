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

package org.fs.app.currency.view.adapter

import android.content.Context
import android.view.ViewGroup
import org.fs.app.currency.common.glide.GlideApp
import org.fs.app.currency.common.manager.CurrencyToFlagUrlManager
import org.fs.app.currency.common.manager.RateManager
import org.fs.app.currency.model.entity.RateEntity
import org.fs.app.currency.view.holder.BaseRateViewHolder
import org.fs.app.currency.view.holder.SimpleRateViewHolder
import org.fs.architecture.mvi.common.ForActivity
import org.fs.architecture.mvi.core.AbstractRecyclerViewAdapter
import org.fs.architecture.mvi.util.ObservableList
import javax.inject.Inject

@ForActivity
class RateAdapter @Inject constructor(
  private val rateManager: RateManager,
  private val currencyToFlagUrlManager: CurrencyToFlagUrlManager,
  dataSet: ObservableList<RateEntity>,
  context: Context): AbstractRecyclerViewAdapter<RateEntity, BaseRateViewHolder>(dataSet) {

  private val glide by lazy { GlideApp.with(context) }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRateViewHolder = SimpleRateViewHolder(parent, rateManager, currencyToFlagUrlManager, glide)
}
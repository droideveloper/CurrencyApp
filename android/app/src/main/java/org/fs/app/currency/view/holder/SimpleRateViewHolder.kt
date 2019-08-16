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

package org.fs.app.currency.view.holder

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.view_simple_rate_item.view.*
import org.fs.app.currency.R
import org.fs.app.currency.common.glide.GlideRequests
import org.fs.app.currency.common.manager.CurrencyToFlagUrlManager
import org.fs.app.currency.common.manager.RateManager
import org.fs.app.currency.common.manager.delegate.OnRateChangeListener
import org.fs.app.currency.common.manager.delegate.OnRatesChangeListener
import org.fs.app.currency.model.RateEntity
import org.fs.app.currency.util.log
import org.fs.app.currency.util.toCircleDrawableTarget
import org.fs.architecture.mvi.util.inflate
import org.fs.architecture.mvi.util.plusAssign
import org.fs.rx.extensions.util.clicks
import org.fs.rx.extensions.util.textChanges
import java.text.DecimalFormat
import java.text.ParseException
import java.util.*

class SimpleRateViewHolder(view: View,
  private val rateManager: RateManager,
  private val currencyToFlagUrlManager: CurrencyToFlagUrlManager,
  private val glide: GlideRequests): BaseRateViewHolder(view), OnRateChangeListener, OnRatesChangeListener {

  private val context by lazy { itemView.context }
  private val inputMethodManager by lazy { context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }
  private val disposeBag by lazy { CompositeDisposable() }

  private val viewCountryFlagImage by lazy { itemView.viewCountryFlagImage }
  private val viewCurrencyCodeText by lazy { itemView.viewCurrencyCodeText }
  private val viewCurrencyNameText by lazy { itemView.viewCurrencyNameText }

  private val viewAmountEditText by lazy { itemView.viewAmountEditText }

  private var value: RateEntity = RateEntity.EMPTY

  constructor(parent: ViewGroup,
    rateManager: RateManager,
    currencyToFlagUrlManager: CurrencyToFlagUrlManager,
    glide: GlideRequests): this(parent.inflate(R.layout.view_simple_rate_item), rateManager, currencyToFlagUrlManager, glide)

  override fun bind(value: RateEntity) {
    // hold ref
    this.value = value
    // we do put it in here
    val amountString = rateManager.format(value.amount)
    if (value.amount != 0.0) {
      viewAmountEditText.setText(amountString)
    }

    // register callbacks
    rateManager.addOnRatesChangedListener(this)
    rateManager.addOnRateChangedListener(this)

    val uri = currencyToFlagUrlManager.countryFlagUrlFor(value.base)
    if (uri != Uri.EMPTY) {
      glide.clear(viewCountryFlagImage)
      glide.asBitmap()
        .load(uri)
        .applyCircularCrop()
        .into(viewCountryFlagImage.toCircleDrawableTarget())
    }

    viewCurrencyCodeText.text = value.base

    val currency = Currency.getInstance(value.base)
    viewCurrencyNameText.text = currency.displayName

    // select currency
    disposeBag += bindNewRate(value).subscribe { newRate -> rateManager.rate = newRate }
    // bind rate change
    disposeBag += bindRateAmountChange(value).subscribe { newAmount -> rateManager.rate = newAmount }

    // request focus
    requestFocusIfNeeded(value)
    maybeUpdateCurrencies()
  }

  override fun unbind() = disposeBag.clear().also {
    rateManager.removeOnRateChangedListener(this)
    rateManager.removeOnRatesChangedListener(this)
  }

  private fun bindNewRate(value: RateEntity): Observable<RateEntity> = itemView.clicks()
    .map {
      val text = viewAmountEditText.text
      // defaults will be null we can not parse it
      val amount = try {
        rateManager.parse(text.toString())
      } catch (error: ParseException) {
        0.0
      }
      return@map RateEntity().apply {
        this.base = value.base
        this.amount = amount
      }
    }

  private fun bindRateAmountChange(value: RateEntity): Observable<RateEntity> = viewAmountEditText.textChanges()
    .filter { TextUtils.equals(value.base, rateManager.rate.base) }
    .map { text ->
      // defaults will be null we can not parse it
      val amount = try {
        rateManager.parse(text.toString())
      } catch (error: ParseException) {
        0.0
      }
      value.amount = amount
      return@map value
    }

  override fun onRateChange(newValue: RateEntity) = requestFocusIfNeeded(newValue)

  override fun onRatesChange(newRates: MutableMap<String, Double>) = maybeUpdateCurrencies()

  private fun requestFocusIfNeeded(newValue: RateEntity) {
    val shouldHaveFocus = adapterPosition == 0
    if (TextUtils.equals(value.base, newValue.base) && shouldHaveFocus) {
      viewAmountEditText.requestFocus()
      inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS)
    }
    maybeUpdateCurrencies()
  }

  private fun maybeUpdateCurrencies() {
    val amount = rateManager.amountForCurrency(value.base)
    val amountString = rateManager.format(amount)
    val currentRateBase = rateManager.rate.base
    if (!TextUtils.equals(currentRateBase, value.base)) {
      // hold reference to it
      value.amount = amount
      if (amount == 0.0) {
        val charSequence: CharSequence? = null
        viewAmountEditText.setText(charSequence)
      } else {
        viewAmountEditText.setText(amountString)
      }
    }
  }
}
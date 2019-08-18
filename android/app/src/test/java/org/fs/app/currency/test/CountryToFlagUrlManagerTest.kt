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

package org.fs.app.currency.test

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.fs.app.currency.common.base.TestCase
import org.fs.app.currency.common.dagger.TestInjection
import org.fs.app.currency.common.manager.CurrencyToCountryManager
import org.fs.app.currency.common.manager.CurrencyToFlagUrlManager
import org.fs.app.currency.common.repo.CountryCurrenciesRepository
import org.fs.app.currency.net.model.Resource
import org.fs.app.currency.rule.RxSchedulersTestRule
import org.fs.app.currency.util.C.Companion.EUROPE_CURRENCY
import org.fs.architecture.mvi.util.EMPTY
import org.fs.architecture.mvi.util.plusAssign
import org.junit.After
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class CountryToFlagUrlManagerTest: TestCase {

  companion object {
    @JvmField @ClassRule val schedulersRule = RxSchedulersTestRule()
  }

  @Inject lateinit var countryToFlagUrlManager: CurrencyToFlagUrlManager

  @Inject lateinit var countryToCurrencyToCountryManager: CurrencyToCountryManager
  @Inject lateinit var countryCurrenciesRepository: CountryCurrenciesRepository

  private val disposeBag by lazy { CompositeDisposable() }

  @Before fun setUp() = TestInjection.inject(this)

  @Test fun testCountryToFlagManager() {
    val letch = CountDownLatch(1)

    disposeBag += countryCurrenciesRepository.countryCurrencies()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe { resource ->
        if (resource is Resource.Success<Map<String, String>>) {
          countryToCurrencyToCountryManager.populateCache(resource.data ?: emptyMap())
        }

        letch.countDown()
      }

    letch.await()

    val url = countryToFlagUrlManager.countryFlagUrlFor(EUROPE_CURRENCY)
    assert(url != String.EMPTY)
  }

  @After fun cleanUp() = disposeBag.clear().also {
    countryToCurrencyToCountryManager.clearAll()
  }
}
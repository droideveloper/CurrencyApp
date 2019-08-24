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

import org.fs.app.currency.common.base.TestCase
import org.fs.app.currency.common.dagger.TestInjection
import org.fs.app.currency.common.manager.RateManager
import org.fs.app.currency.common.manager.delegate.OnRateChangeListener
import org.fs.app.currency.common.manager.delegate.OnRatesChangeListener
import org.fs.app.currency.model.entity.RateEntity
import org.fs.app.currency.rule.RxSchedulersTestRule
import org.fs.app.currency.util.C.Companion.EUROPE_CURRENCY
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class RateManagerTest: TestCase {

  companion object {
    @JvmField @ClassRule val schedulersRule = RxSchedulersTestRule()
  }

  @Inject lateinit var rateManager: RateManager

  @Before fun setUp() = TestInjection.inject(this).also {
    rateManager.rate = RateEntity.EMPTY
    rateManager.rates = emptyMap()
  }

  @Test fun testRateManagerRateCallback() {
    val letch = CountDownLatch(1)

    val rate = RateEntity().apply {
      base = EUROPE_CURRENCY
      amount = 1.0
    }

    val callback = OnRateChangeListener { newRate ->
      assert(rate == newRate)

      letch.countDown()
    }

    rateManager.addOnRateChangedListener(callback)
    rateManager.rate = rate

    letch.await()

    rateManager.removeOnRateChangedListener(callback)
  }

  @Test fun testRateManagerRatesCallback() {
    val letch = CountDownLatch(1)

    val rates = HashMap<String, Double>().apply {
      put(EUROPE_CURRENCY, 1.0)
    }

    val callback = OnRatesChangeListener { newRates ->
      assert(rates == newRates)

      letch.countDown()
    }

    rateManager.addOnRatesChangedListener(callback)
    rateManager.rates = rates

    letch.await()

    rateManager.removeOnRatesChangedListener(callback)
  }
}
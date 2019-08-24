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
import org.fs.app.currency.mock.RateActivity
import org.fs.app.currency.model.entity.RateEntity
import org.fs.app.currency.rule.RxSchedulersTestRule

import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class RateViewTest: TestCase {

  companion object {
    @JvmField @ClassRule val schedulerRule = RxSchedulersTestRule()
  }

  @Inject lateinit var rateActivity: RateActivity
  @Inject lateinit var rateManager: RateManager

  @Before fun setUp() = TestInjection.inject(this).also {
    rateManager.rates = emptyMap()
    rateManager.rate = RateEntity.EMPTY
    rateActivity.create()
  }

  @Test fun testRateActivity() {
    val letch = CountDownLatch(1)

    val block = {
      assert(rateManager.rates.isNotEmpty())
      letch.countDown()

      rateActivity.detach()
    }

    assert(rateManager.rates.isEmpty())
    assert(!rateActivity.showingProgress.get())
    rateActivity.block = block
    rateActivity.attach()

    assert(rateActivity.showingProgress.get())

    letch.await()
  }
}
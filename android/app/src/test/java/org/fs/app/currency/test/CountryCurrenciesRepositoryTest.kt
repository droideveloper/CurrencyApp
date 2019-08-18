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
import org.fs.app.currency.common.repo.CountryCurrenciesRepository
import org.fs.app.currency.net.model.Resource
import org.fs.app.currency.rule.RxSchedulersTestRule
import org.fs.architecture.mvi.util.plusAssign
import org.junit.After
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import javax.inject.Inject

class CountryCurrenciesRepositoryTest: TestCase {

  companion object {
    @JvmField @ClassRule val schedulersRule = RxSchedulersTestRule()

    private const val ERROR_URL = "https://exampl.org?errorQuery=failed"
    private const val SUCCESS_URL = "https://example.org"
  }

  @Inject lateinit var countryCurrenciesRepository: CountryCurrenciesRepository

  private val disposeBag by lazy { CompositeDisposable() }

  @Before fun setUp() = TestInjection.inject(this)

  @Test fun testCurrenciesSuccess() = request(SUCCESS_URL) {
    return@request this is Resource.Success<Map<String, String>>
  }

  @Test fun testCurrenciesFailure() = request(ERROR_URL) {
    return@request this is Resource.Failure<Map<String, String>>
  }

  private fun request(url: String, block: Resource<Map<String, String>>.() -> Boolean) {
    val letch = CountDownLatch(1)

    disposeBag += countryCurrenciesRepository.countryCurrencies(url)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe { resource ->

        assert(block(resource))

        letch.countDown()
      }

    // await finish
    letch.await()
  }

  @After fun cleanUp() = disposeBag.clear()
}
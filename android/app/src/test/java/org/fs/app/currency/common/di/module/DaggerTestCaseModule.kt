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

package org.fs.app.currency.common.di.module

import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import org.fs.app.currency.common.base.TestCase
import org.fs.app.currency.common.dagger.key.TestCaseKey
import org.fs.app.currency.common.di.component.*
import org.fs.app.currency.test.*

@Module
abstract class DaggerTestCaseModule {

  @Binds
  @IntoMap
  @TestCaseKey(RateRepositoryTest::class)
  abstract fun bindRateRepositoryTestFactory(factory: RateRepositoryTestComponent.Builder): AndroidInjector.Factory<out TestCase>

  @Binds
  @IntoMap
  @TestCaseKey(LandingPageViewTest::class)
  abstract fun bindLandingPageTestFactory(factory: LandingPageViewTestComponent.Builder): AndroidInjector.Factory<out TestCase>

  @Binds
  @IntoMap
  @TestCaseKey(RateViewTest::class)
  abstract fun bindRateViewTestFactory(factory: RateViewTestComponent.Builder): AndroidInjector.Factory<out TestCase>

  @Binds
  @IntoMap
  @TestCaseKey(RateManagerTest::class)
  abstract fun bindRateManagerTestFactory(factory: RateManagerTestComponent.Builder): AndroidInjector.Factory<out TestCase>

  @Binds
  @IntoMap
  @TestCaseKey(CountryCurrenciesRepositoryTest::class)
  abstract fun bindCountryCurrenciesRepositoryTestFactory(factory: CountryCurrenciesRepositoryTestComponent.Builder): AndroidInjector.Factory<out TestCase>

  @Binds
  @IntoMap
  @TestCaseKey(CountryToFlagUrlManagerTest::class)
  abstract fun bindCountryToFlagUrlManagerTestFactory(factory: CountryToFlagUrlManagerTestComponent.Builder): AndroidInjector.Factory<out TestCase>
}
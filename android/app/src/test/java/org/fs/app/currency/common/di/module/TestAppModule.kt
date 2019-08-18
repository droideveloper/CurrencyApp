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
import okhttp3.Interceptor
import org.fs.app.currency.common.di.component.*
import org.fs.app.currency.common.manager.*
import org.fs.app.currency.common.repo.CountryCurrenciesRepository
import org.fs.app.currency.common.repo.CountryCurrenciesRepositoryImp
import org.fs.app.currency.common.repo.RatesRepository
import org.fs.app.currency.common.repo.RatesRepositoryImp
import org.fs.app.currency.net.EndpointProxy
import org.fs.app.currency.net.EndpointProxyImp
import org.fs.app.currency.net.MockInterceptor
import javax.inject.Singleton

@Module(subcomponents = [
  RateRepositoryTestComponent::class,
  CountryCurrenciesRepositoryTestComponent::class,
  LandingPageViewTestComponent::class,
  RateViewTestComponent::class,
  RateManagerTestComponent::class,
  CountryToFlagUrlManagerTestComponent::class,
  // views
  LandingPageActivityComponent::class,
  RateActivityComponent::class
])
abstract class TestAppModule {

  @Singleton @Binds abstract fun bindInterceptor(interceptorImp: MockInterceptor): Interceptor

  @Singleton @Binds abstract fun bindRatesRepository(repo: RatesRepositoryImp): RatesRepository
  @Singleton @Binds abstract fun bindCountryCurrenciesRepository(repo: CountryCurrenciesRepositoryImp): CountryCurrenciesRepository

  @Singleton @Binds abstract fun bindRateManager(manager: RateManagerImp): RateManager
  @Singleton @Binds abstract fun bindCurrencyToCountryManager(manager: CurrencyToCountryManagerImp): CurrencyToCountryManager
  @Singleton @Binds abstract fun bindCurrencyToFlagUrlManager(manager: CurrencyToFlagUrlManagerImp): CurrencyToFlagUrlManager

  @Singleton @Binds abstract fun bindEndpointProxy(proxy: EndpointProxyImp): EndpointProxy
}
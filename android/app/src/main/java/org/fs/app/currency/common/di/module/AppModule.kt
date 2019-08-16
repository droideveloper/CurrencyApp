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

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import org.fs.app.currency.App
import org.fs.app.currency.common.manager.*
import org.fs.app.currency.common.repo.CountryCurrenciesRepository
import org.fs.app.currency.common.repo.CountryCurrenciesRepositoryImp
import org.fs.app.currency.common.repo.RatesRepository
import org.fs.app.currency.common.repo.RatesRepositoryImp
import org.fs.app.currency.net.EndpointProxy
import org.fs.app.currency.net.EndpointProxyImp
import org.fs.app.currency.view.LandingPageActivity
import org.fs.app.currency.view.RateActivity
import org.fs.architecture.mvi.common.ForActivity
import javax.inject.Singleton

@Module
abstract class AppModule {

  @Singleton @Binds abstract fun bindApplication(app: App): Application
  @Singleton @Binds abstract fun bincApplicationContext(app: Application): Context

  @Singleton @Binds abstract fun bindRatesRepository(repo: RatesRepositoryImp): RatesRepository
  @Singleton @Binds abstract fun bindCountryCurrenciesRepository(repo: CountryCurrenciesRepositoryImp): CountryCurrenciesRepository

  @Singleton @Binds abstract fun bindRateManager(manager: RateManagerImp): RateManager
  @Singleton @Binds abstract fun bindCurrencyToCountryManager(manager: CurrencyToCountryManagerImp): CurrencyToCountryManager
  @Singleton @Binds abstract fun bindCurrencyToFlagUrlManager(manager: CurrencyToFlagUrlManagerImp): CurrencyToFlagUrlManager

  @Singleton @Binds abstract fun bindEndpointProxy(proxy: EndpointProxyImp): EndpointProxy

  @ForActivity @ContributesAndroidInjector(modules = [ActivityModule::class, ProviderActivityModule::class])
  abstract fun contributeLandingPageActivity(): LandingPageActivity

  @ForActivity @ContributesAndroidInjector(modules = [ActivityModule::class, ProviderActivityModule::class])
  abstract fun contributeRateActivity(): RateActivity
}
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
import dagger.multibindings.Multibinds
import org.fs.app.currency.common.dagger.key.ViewKey
import org.fs.app.currency.common.di.component.LandingPageActivityComponent
import org.fs.app.currency.common.di.component.RateActivityComponent
import org.fs.app.currency.mock.LandingPageActivity
import org.fs.app.currency.mock.RateActivity
import org.fs.app.currency.view.LandingPageActivityView
import org.fs.app.currency.view.RateActivityView
import org.fs.architecture.mvi.common.ForActivity
import org.fs.architecture.mvi.common.View
import kotlin.reflect.KClass

@Module
abstract class DaggerViewModule {

  @Binds abstract fun bindLandingPageActivityView(activity: LandingPageActivity): LandingPageActivityView

  @Binds abstract fun bindRateActivityView(activity: RateActivity): RateActivityView

  @Binds
  @IntoMap
  @ViewKey(LandingPageActivity::class)
  abstract fun bindLandingPageActivityFactory(factory: LandingPageActivityComponent.Builder): AndroidInjector.Factory<out View>

  @Binds
  @IntoMap
  @ViewKey(RateActivity::class)
  abstract fun bindRateActivityFactory(factory: RateActivityComponent.Builder): AndroidInjector.Factory<out View>
}
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

import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.Multibinds
import org.fs.app.currency.common.base.TestCase
import org.fs.architecture.mvi.common.View
import kotlin.reflect.KClass

@Module
abstract class DaggerTestModule {

  @Multibinds abstract fun bindTestCaseInjectorFactories(): Map<KClass<out TestCase>, AndroidInjector.Factory<out TestCase>>
  @Multibinds abstract fun bindTestCaseInjectorFactoriesStringKeys(): Map<String, AndroidInjector.Factory<out TestCase>>

  @Multibinds abstract fun bindViewInjectorFactories(): Map<KClass<out View>, AndroidInjector.Factory<out View>>
  @Multibinds abstract fun bindViewInjectorFactoriesStringKeys(): Map<String, AndroidInjector.Factory<out View>>
}
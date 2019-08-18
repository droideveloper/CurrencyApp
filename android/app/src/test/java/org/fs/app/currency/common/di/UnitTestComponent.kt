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

package org.fs.app.currency.common.di

import dagger.Component
import dagger.android.AndroidInjector
import org.fs.app.currency.common.UnitTest
import org.fs.app.currency.common.di.module.*
import javax.inject.Singleton

@Singleton
@Component(modules = [
  DaggerTestModule::class,
  DaggerTestCaseModule::class,
  DaggerViewModule::class,
  DaggerProviderViewModule::class,
  TestAppModule::class,
  TestNetworkModule::class])
interface UnitTestComponent: AndroidInjector<UnitTest> {

  @Component.Builder
  abstract class Builder: AndroidInjector.Builder<UnitTest>()
}
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

package org.fs.app.currency.common.dagger

import org.fs.app.currency.common.UnitTest
import org.fs.app.currency.common.base.TestCase
import org.fs.architecture.mvi.common.View

sealed class TestInjection {

  companion object {

    @JvmStatic fun <T> inject(test: T) where T: TestCase {
      val unit = UnitTest.sharedInstance()
      val injector = unit as HasTestCaseInjector
      // we do injection
      val injection = injector.testCaseInjector()
      injection.inject(test)
    }

    @JvmStatic fun <T> inject(view: T) where T: View {
      val unit = UnitTest.sharedInstance()
      val injector = unit as HasViewInjector
      // we do injection
      val injection = injector.viewInjector()
      injection.inject(view)
    }
  }
}
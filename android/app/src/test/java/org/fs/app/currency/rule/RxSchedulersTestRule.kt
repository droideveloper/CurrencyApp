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

package org.fs.app.currency.rule

import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins

class RxSchedulersTestRule: TestRule {

  private val testScheduler by lazy { object: Scheduler() {

      override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
        return super.scheduleDirect(run, 0, unit)
      }

      override fun createWorker(): Worker = ExecutorScheduler.ExecutorWorker(Executor { it.run() }, false)
    }
  }

  override fun apply(base: Statement?, description: Description?): Statement {
    return object: Statement() {

      @Throws(Throwable::class)
      override fun evaluate() {
        RxJavaPlugins.setInitIoSchedulerHandler { testScheduler }
        RxJavaPlugins.setInitComputationSchedulerHandler { testScheduler }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { testScheduler }
        RxJavaPlugins.setInitSingleSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { testScheduler }

        try {
          base?.evaluate()
        } finally {
          RxJavaPlugins.reset()
          RxAndroidPlugins.reset()
        }
      }
    }
  }
}
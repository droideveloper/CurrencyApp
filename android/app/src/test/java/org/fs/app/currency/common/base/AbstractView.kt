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

package org.fs.app.currency.common.base

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentManager
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.fs.app.currency.common.dagger.TestInjection
import org.fs.architecture.mvi.common.Event
import org.fs.architecture.mvi.common.Model
import org.fs.architecture.mvi.common.View
import org.fs.architecture.mvi.common.ViewModel
import org.fs.architecture.mvi.util.EMPTY
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

abstract class AbstractView<T, VM>: View where VM: ViewModel<T>, T: Model<*> {

  @Inject lateinit var viewModel: VM
  @Inject lateinit var context: Context
  @Inject lateinit var fragmentManager: FragmentManager

  protected val disposeBag by lazy { CompositeDisposable() }
  protected val viewEvents by lazy { PublishRelay.create<Event>() }

  private val available by lazy { AtomicBoolean(false) }

  open fun create() {
    available.getAndSet(true)
  }

  abstract fun attach()
  abstract fun detach()

  override fun startActivity(intent: Intent?) = context.startActivity(intent)

  override fun startActivityForResult(intent: Intent?, requestCode: Int) = context.startActivity(intent)

  override fun finish() = available.set(false)

  override fun dismiss() = available.set(false)

  override fun supportFragmentManager(): FragmentManager = fragmentManager

  override fun stringResource(stringRes: Int): String? = context.getString(stringRes)

  override fun isAvailable(): Boolean = available.get()

  override fun context(): Context? = context

  override fun viewEvents(): Observable<Event> = viewEvents.hide()
}
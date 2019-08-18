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

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentManager
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.fs.app.currency.BuildConfig
import org.fs.app.currency.common.di.component.*
import org.fs.app.currency.common.moshi.DateAdapter
import org.fs.app.currency.net.Endpoint
import org.fs.architecture.mvi.util.EMPTY
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
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
class TestNetworkModule {

  @Singleton @Provides fun provideBaseUrl(): HttpUrl = HttpUrl.parse(BuildConfig.BASE_URL) ?: throw IllegalArgumentException("invalid url ${BuildConfig.BASE_URL}")

  @Provides @Singleton fun provideHttpClient(interceptor: Interceptor): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(interceptor)
    .build()

  @Singleton @Provides fun provideMoshi(): Moshi = Moshi.Builder()
    .add(DateAdapter())
    .build()

  @Singleton @Provides fun provideRetrofit(baseUrl: HttpUrl, client: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(client)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()

  @Singleton @Provides fun provideEndpoint(retrofit: Retrofit): Endpoint = retrofit.create(Endpoint::class.java)

  @Singleton @Provides fun provideMockContext(): Context {
    return mock(Context::class.java).also { context ->
      // apply mock states
      `when`(context.getString(any(Int::class.java)))
        .thenReturn(String.EMPTY)
      `when`(context.startActivity(any(Intent::class.java)))
    }
  }

  @Singleton @Provides fun provideMockFragmentManager(): FragmentManager {
    return mock(FragmentManager::class.java)
  }
}
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
import dagger.Provides
import org.fs.rx.extensions.common.Variable
import java.util.*
import javax.inject.Singleton

@Module
class ProviderAppModule {

  @Singleton @Provides fun provideRate(): Variable<Map<String, Double>> = Variable(Collections.emptyMap())

}
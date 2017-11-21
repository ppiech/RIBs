/*
 * Copyright (C) 2017. Uber Technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uber.rib.root.logged_out

import java.lang.annotation.RetentionPolicy.CLASS

import android.view.LayoutInflater
import android.view.ViewGroup
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import com.uber.rib.root.UserName
import com.uber.rib.tutorial4.R
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import java.lang.annotation.Retention
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Builder for the [LoggedOutScope].
 */
class LoggedOutBuilder : ViewBuilder<LoggedOutView, LoggedOutRouter, Any>(Object()) {

    /**
     * Builds a new [LoggedOutRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [LoggedOutRouter].
     */
    fun build(parentViewGroup: ViewGroup, requestLogin: (UserName, UserName) -> Unit): LoggedOutRouter {
        val view = createView(parentViewGroup)
        val interactor = LoggedOutInteractor(requestLogin, view)
        return LoggedOutRouter(view, interactor);
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): LoggedOutView {
        return inflater.inflate(R.layout.logged_out_rib, parentViewGroup, false) as LoggedOutView
    }

//    interface ParentComponent {
//
//        fun listener(): LoggedOutInteractor.Listener
//    }
//
//    @dagger.Module
//    abstract class Module {
//
//        @LoggedOutScope
//        @Binds
//        internal abstract fun presenter(view: LoggedOutView): LoggedOutInteractor.LoggedOutPresenter
//
//        companion object {
//
//            @LoggedOutScope
//            @Provides
//            internal fun router(
//                component: Component,
//                view: LoggedOutView,
//                interactor: LoggedOutInteractor): LoggedOutRouter {
//                return LoggedOutRouter(view, interactor, component)
//            }
//        }
//
//        // TODO: Create provider methods for dependencies created by this Rib. These should be static.
//    }

//    @LoggedOutScope
//    @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
//    internal interface Component : InteractorBaseComponent<LoggedOutInteractor>, BuilderComponent {
//
//        @dagger.Component.Builder
//        interface Builder {
//
//            @BindsInstance
//            fun interactor(interactor: LoggedOutInteractor): Builder
//
//            @BindsInstance
//            fun view(view: LoggedOutView): Builder
//
//            fun parentComponent(component: ParentComponent): Builder
//
//            fun build(): Component
//        }
//    }
//
//    internal interface BuilderComponent {
//
//        fun loggedoutRouter(): LoggedOutRouter
//    }
//
//    @Scope
//    @Retention(CLASS)
//    internal annotation class LoggedOutScope
//
//    @Qualifier
//    @Retention(CLASS)
//    internal annotation class LoggedOutInternal
}

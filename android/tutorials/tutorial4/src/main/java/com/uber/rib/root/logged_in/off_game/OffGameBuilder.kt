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

package com.uber.rib.root.logged_in.off_game

import android.view.LayoutInflater
import android.view.ViewGroup
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import com.uber.rib.root.UserName
import com.uber.rib.root.logged_in.GameKey
import com.uber.rib.root.logged_in.ScoreStream
import com.uber.rib.tutorial4.R

/**
 * Builder for the [OffGameScope].
 */
class OffGameBuilder() : ViewBuilder<OffGameView, OffGameRouter, Any>(Object()) {

    /**
     * Builds a new [OffGameRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [OffGameRouter].
     */
    fun build(parentViewGroup: ViewGroup, playerOne: UserName, playerTwo: UserName, onStartGame: (GameKey) -> Unit,
              scoreStream: ScoreStream, gameNames: List<GameKey>)
        : OffGameRouter
    {
        val view = createView(parentViewGroup)
        val interactor = OffGameInteractor(playerOne, playerTwo, onStartGame, view, scoreStream, gameNames)
//        val component = DaggerOffGameBuilder_Component.builder()
//            .parentComponent(dependency)
//            .view(view)
//            .interactor(interactor)
//            .build()
        val component = Component()
        return OffGameRouter(view, interactor, component)
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): OffGameView {
        return inflater.inflate(R.layout.off_game_rib, parentViewGroup, false) as OffGameView
    }

    interface ParentComponent {
//        @Named("player_one")
//        fun playerOne(): UserName
//
//        @Named("player_two")
//        fun playerTwo(): UserName
//
//        fun listener(): OffGameInteractor.Listener
//        fun scoreStream(): ScoreStream
//        fun gameKeys(): List<GameKey>
    }

//    @dagger.Module
//    abstract class Module {
//
//        @OffGameScope
//        @Binds
//        internal abstract fun presenter(view: OffGameView): OffGameInteractor.OffGamePresenter
//
//        companion object {
//
//            @OffGameScope
//            @Provides
//            internal fun router(
//                component: Component,
//                view: OffGameView,
//                interactor: OffGameInteractor): OffGameRouter {
//                return OffGameRouter(view, interactor, component)
//            }
//        }
//    }

    internal class Component : InteractorBaseComponent<OffGameInteractor> {
        override fun inject(interactor: OffGameInteractor?) {
            // not used
        }
    }

//        @dagger.Component.Builder
//        interface Builder {
//
//            @BindsInstance
//            fun interactor(interactor: OffGameInteractor): Builder
//
//            @BindsInstance
//            fun view(view: OffGameView): Builder
//
//            fun parentComponent(component: ParentComponent): Builder
//
//            fun build(): Component
//        }
//    }

//    @Scope
//    @Retention(CLASS)
//    internal annotation class OffGameScope
//
//    @Qualifier
//    @Retention(CLASS)
//    internal annotation class OffGameInternal
}

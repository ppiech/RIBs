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

package com.uber.rib.root.logged_in.tic_tac_toe

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
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Builder for the [TicTacToeScope].
 */
class TicTacToeBuilder() : ViewBuilder<TicTacToeView, TicTacToeRouter, Any>(Object()) {

    /**
     * Builds a new [TicTacToeRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [TicTacToeRouter].
     */
    fun build(parentViewGroup: ViewGroup, board: Board, onGameWon: (UserName?) -> Unit,
              playerOne: UserName, playerTwo: UserName):
        TicTacToeRouter
    {
        val view = createView(parentViewGroup)
        val interactor = TicTacToeInteractor(board, onGameWon, view, playerOne, playerTwo)
        return TicTacToeRouter(view, interactor);
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): TicTacToeView {
        return inflater.inflate(R.layout.tic_tac_toe_rib, parentViewGroup, false) as TicTacToeView
    }

//    interface ParentComponent {
//        fun ticTacToeListener(): TicTacToeInteractor.Listener
//
//        @Named("player_one")
//        fun playerOne(): UserName
//
//        @Named("player_two")
//        fun playerTwo(): UserName
//    }

//    @dagger.Module
//    abstract class Module {
//
//        @TicTacToeScope
//        @Binds
//        internal abstract fun presenter(view: TicTacToeView): TicTacToeInteractor.TicTacToePresenter
//
//        companion object {
//
//            @TicTacToeScope
//            @Provides
//            internal fun router(
//                component: Component,
//                view: TicTacToeView,
//                interactor: TicTacToeInteractor): TicTacToeRouter {
//                return TicTacToeRouter(view, interactor, component)
//            }
//        }
//    }

//    @TicTacToeScope
//    @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
//    internal interface Component : InteractorBaseComponent<TicTacToeInteractor>, BuilderComponent {
//
//        @dagger.Component.Builder
//        interface Builder {
//
//            @BindsInstance
//            fun interactor(interactor: TicTacToeInteractor): Builder
//
//            @BindsInstance
//            fun view(view: TicTacToeView): Builder
//
//            fun parentComponent(component: ParentComponent): Builder
//
//            fun build(): Component
//        }
//    }
//
//    internal interface BuilderComponent {
//
//        fun tictactoeRouter(): TicTacToeRouter
//    }

//    @Scope
//    @Retention(CLASS)
//    internal annotation class TicTacToeScope
//
//    @Qualifier
//    @Retention(CLASS)
//    internal annotation class TicTacToeInternal
}

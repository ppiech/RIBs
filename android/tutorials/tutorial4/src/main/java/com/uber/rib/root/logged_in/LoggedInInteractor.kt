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

package com.uber.rib.root.logged_in

import android.view.ViewGroup
import com.google.common.collect.Lists
import com.uber.rib.core.*
import com.uber.rib.root.UserName
import com.uber.rib.root.logged_in.random_winner.RandomWinnerBuilder
import com.uber.rib.root.logged_in.tic_tac_toe.Board
import com.uber.rib.root.logged_in.tic_tac_toe.TicTacToeBuilder

import kotlin.reflect.KProperty

/**
 * Coordinates Business Logic for [LoggedInScope].
 */
@RibInteractor
class LoggedInInteractor(val board: Board, val playerOne : UserName, val playerTwo : UserName)
    : Interactor<EmptyPresenter, LoggedInRouter>(), LoggedInActionableItem
{
    init {
        setPresenter(EmptyPresenter());
    }

    internal var scoreStream: MutableScoreStream = MutableScoreStream(playerOne, playerTwo);

    internal val gameProviders: List<GameProvider> by {

    }

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        // when first logging in we should be in the OffGame state
        router.attachOffGame(this::onStartGame, scoreStream, gameProviders)
    }

    fun onStartGame(gameKey: GameKey) {
        router.detachOffGame()
        for (gameProvider in gameProviders!!) {
            if (gameProvider.gameName() == gameKey.gameName()) {
                router.attachGame(gameProvider)
            }
        }
    }

    fun onGameWon(winner: UserName?) {
        if (winner != null) {
            scoreStream!!.addVictory(winner)
        }

        router.detachGame()
        router.attachOffGame(this::onStartGame, scoreStream, gameProviders)
    }
}

private operator fun <R> (() -> R).getValue(thisRef: LoggedInInteractor, property: KProperty<*>): List<GameProvider> {
    val ticTacToeGame = object : GameProvider {
        override fun gameName(): String {
            return "TicTacToe"
        }

        override fun viewRouter(viewGroup: ViewGroup): ViewRouter<*, *, *> {
            return TicTacToeBuilder().build(viewGroup, thisRef.board , thisRef::onGameWon, thisRef.playerOne, thisRef.playerTwo)
        }
    }
    val randomWinnerGame = object : GameProvider {
        override fun gameName(): String {
            return "RandomWinner"
        }

        override fun viewRouter(viewGroup: ViewGroup): ViewRouter<*, *, *> {
            return RandomWinnerBuilder().build(viewGroup, thisRef::onGameWon, thisRef.playerOne, thisRef.playerTwo)
        }
    }

    return Lists.newArrayList(ticTacToeGame, randomWinnerGame)
}

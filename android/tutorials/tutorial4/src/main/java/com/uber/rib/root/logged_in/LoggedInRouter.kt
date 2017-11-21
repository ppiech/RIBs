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
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.Router
import com.uber.rib.core.ViewRouter
import com.uber.rib.root.UserName
import com.uber.rib.root.logged_in.off_game.OffGameBuilder
import com.uber.rib.root.logged_in.off_game.OffGameRouter
import com.uber.rib.root.logged_in.tic_tac_toe.TicTacToeBuilder

/**
 * Adds and removes children of [LoggedInBuilder.LoggedInScope].
 */
class LoggedInRouter internal constructor(
    interactor: LoggedInInteractor,
    private val parentView: ViewGroup,
    private val playerOne: UserName,
    private val playerTwo: UserName,
    private val offGameBuilder: OffGameBuilder)
    : Router<LoggedInInteractor, InteractorBaseComponent<LoggedInInteractor>>(interactor, InteractorBaseComponent { })

{
    private var offGameRouter: OffGameRouter? = null
    private var gameRouter: ViewRouter<*, *, *>? = null

    override fun willDetach() {
        super.willDetach()
        detachOffGame()
        detachGame()
    }

    internal fun attachOffGame(onStartGame: (GameKey) -> Unit,
                               scoreStream: ScoreStream, gameNames: List<GameKey>)
    {
        offGameRouter = offGameBuilder.build(parentView, playerOne, playerTwo, onStartGame, scoreStream, gameNames);
        attachChild(offGameRouter)
        parentView.addView(offGameRouter!!.view)
    }

    internal fun detachOffGame() {
        if (offGameRouter != null) {
            detachChild(offGameRouter)
            parentView.removeView(offGameRouter!!.view)
            offGameRouter = null
        }
    }

    internal fun attachGame(gameProvider: GameProvider) {
        gameRouter = gameProvider.viewRouter(parentView)
        parentView.addView(gameRouter!!.view)
        attachChild(gameRouter)
    }

    internal fun detachGame() {
        if (gameRouter != null) {
            detachChild(gameRouter)
            parentView.removeView(gameRouter!!.view)
            gameRouter = null
        }
    }
}

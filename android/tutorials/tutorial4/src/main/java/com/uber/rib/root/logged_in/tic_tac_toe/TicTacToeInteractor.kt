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

import android.annotation.SuppressLint
import com.uber.rib.core.Bundle
import com.uber.rib.core.EmptyPresenter
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import com.uber.rib.root.UserName
import com.uber.rib.root.logged_in.tic_tac_toe.Board.MarkerType
import io.reactivex.Observable

/**
 * Coordinates Business Logic for [TicTacToeScope].
 */
@RibInteractor
class TicTacToeInteractor(val board: Board, val onGameWon: (UserName?) -> Unit, val presenter: TicTacToePresenter,
                          val playerOne: UserName, val playerTwo: UserName)
    : Interactor<TicTacToeInteractor.TicTacToePresenter, TicTacToeRouter>()
{

    init {
        setPresenter(presenter);
    }

    private var currentPlayer = MarkerType.CROSS

    @SuppressLint("RxLeakedSubscription", "RxSubscribeOnError")
    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        presenter
            .squareClicks()
            .subscribe { xy ->
                if (board.cells[xy.x][xy.y] == null) {
                    if (currentPlayer == MarkerType.CROSS) {
                        board.cells[xy.x][xy.y] = MarkerType.CROSS
                        board.currentRow = xy.x
                        board.currentCol = xy.y
                        presenter.addCross(xy)
                        currentPlayer = MarkerType.NOUGHT
                    } else {
                        board.cells[xy.x][xy.y] = MarkerType.NOUGHT
                        board.currentRow = xy.x
                        board.currentCol = xy.y
                        presenter.addNought(xy)
                        currentPlayer = MarkerType.CROSS
                    }
                }
                if (board.hasWon(MarkerType.CROSS)) {
                    presenter.setPlayerWon(playerOne.userName)
                    onGameWon(playerOne)
                } else if (board.hasWon(MarkerType.NOUGHT)) {
                    presenter.setPlayerWon(playerTwo.userName)
                    onGameWon(playerTwo)
                } else if (board.isDraw) {
                    presenter.setPlayerTie()
                    onGameWon(null)
                } else {
                    updateCurrentPlayer()
                }
            }
        updateCurrentPlayer()
    }

    private fun updateCurrentPlayer() {
        if (currentPlayer == MarkerType.CROSS) {
            presenter.setCurrentPlayerName(playerOne.userName)
        } else {
            presenter.setCurrentPlayerName(playerTwo.userName)
        }
    }


    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface TicTacToePresenter {
        fun squareClicks(): Observable<BoardCoordinate>

        fun setCurrentPlayerName(currentPlayer: String)

        fun setPlayerWon(playerName: String)

        fun setPlayerTie()

        fun addCross(xy: BoardCoordinate)

        fun addNought(xy: BoardCoordinate)
    }

}

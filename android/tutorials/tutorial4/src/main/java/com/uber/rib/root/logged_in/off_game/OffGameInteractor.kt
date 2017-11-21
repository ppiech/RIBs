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

import android.annotation.SuppressLint
import com.google.common.collect.ImmutableMap
import com.uber.autodispose.ObservableScoper
import com.uber.autodispose.ObservableSubscribeProxy
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import com.uber.rib.root.UserName
import com.uber.rib.root.logged_in.GameKey
import com.uber.rib.root.logged_in.ScoreStream

import io.reactivex.Observable
import io.reactivex.functions.Consumer

@SuppressLint("RxLeakedSubscription", "RxSubscribeOnError")
@RibInteractor
class OffGameInteractor(val playerOne: UserName, val playerTwo: UserName, val onStartGame: (GameKey) -> Unit,
                        val presenter: OffGamePresenter, val scoreStream: ScoreStream, val gameNames: List<GameKey>)
    : Interactor<OffGameInteractor.OffGamePresenter, OffGameRouter>() {

    init {
        setPresenter(presenter);
    }

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        presenter.setPlayerNames(playerOne.userName, playerTwo.userName)
        presenter
            .startGameRequest(gameNames)
            .subscribe { gameKey -> onStartGame(gameKey) }

        scoreStream.scores()
            .to<ObservableSubscribeProxy<ImmutableMap<UserName, Int>>>(ObservableScoper(this))
            .subscribe(Consumer<ImmutableMap<UserName, Int>> { scores ->
                val playerOneScore = scores[playerOne]
                val playerTwoScore = scores[playerTwo]
                presenter.setScores(playerOneScore, playerTwoScore)
            })
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface OffGamePresenter {

        fun setPlayerNames(playerOne: String, playerTwo: String)

        fun setScores(playerOneScore: Int?, playerTwoScore: Int?)

        fun startGameRequest(gameKeys: List<GameKey>): Observable<GameKey>
    }
}

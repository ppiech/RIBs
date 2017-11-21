package com.uber.rib.root.logged_in.random_winner

import com.uber.rib.core.Bundle
import com.uber.rib.core.EmptyPresenter
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import com.uber.rib.root.UserName

import java.util.Random

@RibInteractor
class RandomWinnerInteractor(val gameWonListener: (winner: UserName) -> Unit, val playerOne: UserName,
                             val playerTwo: UserName)
    : Interactor<Any, RandomWinnerRouter>()
{

    init {
        setPresenter(EmptyPresenter());
    }

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        if (Random(System.currentTimeMillis()).nextBoolean()) {
            gameWonListener(playerOne)
        } else {
            gameWonListener(playerTwo)
        }
    }

}

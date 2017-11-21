package com.uber.rib.root.logged_in.random_winner

import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of [RandomWinnerBuilder.RandomWinnerScope].
 *
 * TODO describe the possible child configurations of this scope.
 */
class RandomWinnerRouter(
    view: RandomWinnerView,
    interactor: RandomWinnerInteractor)
    : ViewRouter<RandomWinnerView, RandomWinnerInteractor,
    InteractorBaseComponent<RandomWinnerInteractor>>(view, interactor, InteractorBaseComponent { })

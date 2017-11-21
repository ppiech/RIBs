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

package com.uber.rib.root

import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.NoDaggerInteractor
import com.uber.rib.core.RibInteractor
import javax.inject.Inject

/**
 * Coordinates Business Logic for [RootBuilder.RootScope].
 */
@RibInteractor
class RootInteractor(internal var presenter: RootPresenter)
    : Interactor<RootInteractor.RootPresenter, RootRouter>(), RootActionableItem {

    init {
        setPresenter(presenter);
    }

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        router.attachLoggedOut(this::requestLogin)
    }

    fun requestLogin(playerOne: UserName, playerTwo: UserName) {
        // Switch to logged in. Let’s just ignore userName for now.
        router.detachLoggedOut()
        router.attachLoggedIn(playerOne, playerTwo)
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface RootPresenter
}

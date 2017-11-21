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

import com.jakewharton.rxrelay2.BehaviorRelay
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.Optional
import com.uber.rib.core.ViewRouter
import com.uber.rib.root.logged_in.LoggedInActionableItem
import com.uber.rib.root.logged_in.LoggedInBuilder
import com.uber.rib.root.logged_in.LoggedInInteractor
import com.uber.rib.root.logged_in.LoggedInRouter
import com.uber.rib.root.logged_out.LoggedOutBuilder
import com.uber.rib.root.logged_out.LoggedOutRouter

/**
 * Adds and removes children of [RootBuilder.RootScope].
 */
class RootRouter internal constructor(
    view: RootView,
    interactor: RootInteractor,
    private val loggedOutBuilder: LoggedOutBuilder,
    private val loggedInBuilder: LoggedInBuilder)
    : ViewRouter<RootView, RootInteractor, InteractorBaseComponent<RootInteractor>>(
        view, interactor, InteractorBaseComponent {})
{
    private var loggedOutRouter: LoggedOutRouter? = null

    private val loggedInActionableItemRelay = BehaviorRelay.create<Optional<LoggedInActionableItem>>()

    internal fun attachLoggedOut(requestLogin : (UserName, UserName) -> Unit) {
        loggedOutRouter = loggedOutBuilder.build(view, requestLogin)
        attachChild(loggedOutRouter)
        view.addView(loggedOutRouter!!.view)
    }

    internal fun detachLoggedOut() {
        if (loggedOutRouter != null) {
            detachChild(loggedOutRouter)
            view.removeView(loggedOutRouter!!.view)
            loggedOutRouter = null
        }
    }

    internal fun attachLoggedIn(playerOne: UserName, playerTwo: UserName): LoggedInActionableItem {
        // No need to attach views in any way.
        val loggedInRouter = loggedInBuilder.build(view, playerOne, playerTwo)
        attachChild(loggedInRouter)
        loggedInActionableItemRelay.accept(
            Optional.of(loggedInRouter.interactor))
        return loggedInRouter.interactor
    }
}

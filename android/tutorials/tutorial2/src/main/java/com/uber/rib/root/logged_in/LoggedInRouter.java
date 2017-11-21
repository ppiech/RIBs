package com.uber.rib.root.logged_in;

import android.support.annotation.NonNull;
import android.view.View;

import com.uber.rib.core.Router;

/**
 * Adds and removes children of {@link LoggedInBuilder.LoggedInScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
public class LoggedInRouter
        extends Router<LoggedInInteractor, LoggedInBuilder.Component> {

    public LoggedInRouter(
            LoggedInInteractor interactor,
            LoggedInBuilder.Component component) {
        super(interactor, component);
    }
}

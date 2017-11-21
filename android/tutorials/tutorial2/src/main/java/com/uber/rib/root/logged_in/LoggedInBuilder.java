package com.uber.rib.root.logged_in;

import android.support.annotation.NonNull;

import com.uber.rib.core.Builder;
import com.uber.rib.core.EmptyPresenter;
import com.uber.rib.core.InteractorBaseComponent;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;
import javax.inject.Scope;

import dagger.Provides;
import dagger.BindsInstance;

import static java.lang.annotation.RetentionPolicy.CLASS;

public class LoggedInBuilder extends Builder<LoggedInRouter, LoggedInBuilder.ParentComponent> {

    public LoggedInBuilder(ParentComponent dependency) {
        super(dependency);
    }

    /**
     * Builds a new {@link LoggedInRouter}.
     *
     * @return a new {@link LoggedInRouter}.
     */
    public LoggedInRouter build() {
        LoggedInInteractor interactor = new LoggedInInteractor();
        Component component = DaggerLoggedInBuilder_Component.builder()
                .parentComponent(getDependency())
                .interactor(interactor)
                .build();

        return component.loggedinRouter();
    }

    public interface ParentComponent {
        // TODO: Define dependencies required from your parent interactor here.
    }

    @dagger.Module
    public abstract static class Module {

        @LoggedInScope
        @Provides
        static EmptyPresenter presenter() {
            return new EmptyPresenter();
        }

        @LoggedInScope
        @Provides
        static LoggedInRouter router(Component component, LoggedInInteractor interactor) {
            return new LoggedInRouter(interactor, component);
        }

        // TODO: Create provider methods for dependencies created by this Rib. These methods should be static.
    }

    @LoggedInScope
    @dagger.Component(modules = Module.class, dependencies = ParentComponent.class)
    public interface Component extends InteractorBaseComponent<LoggedInInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            Builder interactor(LoggedInInteractor interactor);
            Builder parentComponent(ParentComponent component);
            Component build();
        }

    }

    interface BuilderComponent {
        LoggedInRouter loggedinRouter();
    }

    @Scope
    @Retention(CLASS)
    @interface LoggedInScope { }


    @Qualifier
    @Retention(CLASS)
    @interface LoggedInInternal { }
}

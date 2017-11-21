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

import android.view.LayoutInflater
import android.view.ViewGroup
import com.uber.rib.core.ViewBuilder
import com.uber.rib.root.logged_in.LoggedInBuilder
import com.uber.rib.root.logged_out.LoggedOutBuilder
import com.uber.rib.tutorial4.R

/**
 * Builder for the [RootScope].
 */
class RootBuilder : ViewBuilder<RootView, RootRouter, Any>(Object()) {

    /**
     * Builds a new [RootRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [RootRouter].
     */
    fun build(parentViewGroup: ViewGroup): RootRouter {
        val view = createView(parentViewGroup)
        val interactor = RootInteractor(view)
        //    Component component = DaggerRootBuilder_Component.builder()
        //        .parentComponent(getDependency())
        //        .view(view)
        //        .interactor(interactor)
        //        .build();

        return RootRouter(
            view,
            interactor,
            LoggedOutBuilder(),
            LoggedInBuilder())
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): RootView {
        return inflater.inflate(R.layout.root_rib, parentViewGroup, false) as RootView
    }

    //  public interface ParentComponent {
    //    // Define dependencies required from your parent interactor here.
    //  }
    //
    //  @dagger.Module
    //  public abstract static class Module {
    //
    //    @RootScope
    //    @Provides
    //    static LoggedOutInteractor.Listener loggedOutListener(RootInteractor rootInteractor) {
    //      return rootInteractor.new LoggedOutListener();
    //    }
    //
    //    @RootScope
    //    @Binds
    //    abstract RootInteractor.RootPresenter presenter(RootView view);
    //
    //    @RootScope
    //    @Provides
    //    static RootRouter router(Component component, RootView view, RootInteractor interactor) {
    //      return new RootRouter(
    //          view,
    //          interactor,
    //          component,
    //          new LoggedOutBuilder(component),
    //          new LoggedInBuilder());
    //    }
    //  }

    //  @RootScope
    //  @dagger.Component(
    //      modules = Module.class,
    //      dependencies = ParentComponent.class
    //  )
    //  interface Component extends
    //      InteractorBaseComponent<RootInteractor>,
    //      LoggedOutBuilder.ParentComponent,
    //      BuilderComponent {
    //
    //    @dagger.Component.Builder
    //    interface Builder {
    //
    //      @BindsInstance
    //      Builder interactor(RootInteractor interactor);
    //
    //      @BindsInstance
    //      Builder view(RootView view);
    //
    //      Builder parentComponent(ParentComponent component);
    //
    //      Component build();
    //    }
    //  }
    //
    //  interface BuilderComponent {
    //
    //    RootRouter rootRouter();
    //  }
    //
    //  @Scope
    //  @Retention(CLASS)
    //  @interface RootScope {
    //
    //  }
}

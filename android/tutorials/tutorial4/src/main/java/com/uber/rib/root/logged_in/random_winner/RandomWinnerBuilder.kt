package com.uber.rib.root.logged_in.random_winner

import android.view.LayoutInflater
import android.view.ViewGroup

import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import com.uber.rib.root.UserName
import com.uber.rib.root.logged_in.off_game.OffGameInteractor

import java.lang.annotation.Retention

import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Scope

import dagger.Binds
import dagger.BindsInstance
import dagger.Provides

import java.lang.annotation.RetentionPolicy.CLASS

/**
 * Builder for the [RandomWinnerScope]. Not a real game. This just picks a random winner than exits.
 */
class RandomWinnerBuilder() : ViewBuilder<RandomWinnerView, RandomWinnerRouter, Any>(Object()) {

    /**
     * Builds a new [RandomWinnerRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [RandomWinnerRouter].
     */
    fun build(parentViewGroup: ViewGroup, gameWonListener: (winner: UserName) -> Unit, playerOne: UserName,
              playerTwo: UserName): RandomWinnerRouter
    {
        val view = createView(parentViewGroup)
        val interactor = RandomWinnerInteractor(gameWonListener, playerOne, playerTwo)
//        val component = DaggerRandomWinnerBuilder_Component.builder()
//            .parentComponent(dependency)
//            .view(view)
//            .interactor(interactor)
//            .build()
//
//        return component.randomwinnerRouter()
        return RandomWinnerRouter(view, interactor);
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): RandomWinnerView {
        // Just inflate a silly useless view that does nothing.
        return RandomWinnerView(parentViewGroup.context)
    }

//    @dagger.Module
//    abstract class Module {
//
//        @RandomWinnerScope
//        @Binds
//        internal abstract fun presenter(view: RandomWinnerView): RandomWinnerInteractor.RandomWinnerPresenter
//
//        companion object {
//
//            @RandomWinnerScope
//            @Provides
//            internal fun router(
//                component: Component,
//                view: RandomWinnerView,
//                interactor: RandomWinnerInteractor): RandomWinnerRouter {
//                return RandomWinnerRouter(view, interactor, component)
//            }
//        }
//    }

//    @RandomWinnerScope
//    @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
//    internal interface Component : InteractorBaseComponent<RandomWinnerInteractor>, BuilderComponent {
//
//        @dagger.Component.Builder
//        interface Builder {
//            @BindsInstance
//            fun interactor(interactor: RandomWinnerInteractor): Builder
//
//            @BindsInstance
//            fun view(view: RandomWinnerView): Builder
//
//            fun parentComponent(component: ParentComponent): Builder
//            fun build(): Component
//        }
//    }

//    internal interface BuilderComponent {
//        fun randomwinnerRouter(): RandomWinnerRouter
//    }

//    @Scope
//    @Retention(CLASS)
//    internal annotation class RandomWinnerScope
//
//    @Qualifier
//    @Retention(CLASS)
//    internal annotation class RandomWinnerInternal
}

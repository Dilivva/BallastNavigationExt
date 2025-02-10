package com.dilivva.ballastnavigationext

import androidx.compose.runtime.Composable
import com.copperleaf.ballast.navigation.routing.Destination
import com.copperleaf.ballast.navigation.routing.Route
import com.copperleaf.ballast.navigation.vm.Router

interface Controller<S, R> where R: Route{
    fun getRouter(): Router<R>

    /**
     * navigates to destination
     * @param to [S] Screen to navigate to
     */
    fun navigate(to: S)

    /**
     * Removes the current destination from the backstack
     */
    fun navigateUp()

    /**
     * pop the backstack until the destination, or including the destination if inclusive is true
     * @param inclusive [Boolean] whether the destination should be popped as well
     * @param destination [S]
     */
    fun popUntil(inclusive: Boolean, destination: S)

    /**
     * Used internally by the library.
     * It implements the mapping of route to Screen
     * @param match [Destination.Match]
     * @param appScreen [R]
     */
    fun matchRoute(match: Destination.Match<R>,appScreen: R): S


    /**
     * Provide CompositionLocal object of the controller
     */
    @Composable
    fun provider(block: @Composable () -> Unit)
}
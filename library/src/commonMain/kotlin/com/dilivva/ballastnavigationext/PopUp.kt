package com.dilivva.ballastnavigationext

import com.copperleaf.ballast.navigation.routing.BackstackNavigator
import com.copperleaf.ballast.navigation.routing.Route
import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.routing.addToTop

internal class PopUpTo<T: Route>(
    private val destination: String,
    private val popUpTo: String
): RouterContract.Inputs<T>(){

    override fun BackstackNavigator<T>.navigate() {
        val index = backstack.indexOf(matchDestination(popUpTo))
        val size = backstack.size - (index + 1)

        val newBackStack = if (size > 0) {
            backstack.dropLast(size)
        } else {
            backstack
        }
        updateBackstack {
            newBackStack
        }
        addToTop(destination, emptySet())
    }
}

internal class PopUpAll<T: Route>(
    private val destination: String
): RouterContract.Inputs<T>(){

    override fun BackstackNavigator<T>.navigate() {
        updateBackstack { listOf() }
        addToTop(destination, emptySet())
    }
}
package com.dilivva.ballastnavigationext

import com.copperleaf.ballast.navigation.routing.Destination
import com.copperleaf.ballast.navigation.routing.Route
import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.routing.build
import com.copperleaf.ballast.navigation.routing.directions


/**
 * navigates to [destination]
 * @param destination Screen to navigate to
 * @param extras For more data to be passed, e.g query params or path params
 * For more information see [Navigation](https://copper-leaf.github.io/ballast/wiki/modules/ballast-navigation/#step-4-navigate).
 *
 */
@Deprecated(message = "This will be removed in upcoming release", level = DeprecationLevel.WARNING)
fun <T: Route> Navigator<T>.navigate(
    destination: T,
    extras: Destination.Directions<T>.() -> Destination.Directions<T> = { this }
){
    val route = destination.directions().extras().build()
    trySend(RouterContract.Inputs.GoToDestination(route))
}


/**
 * Navigate up
 * @param T
 */
@Deprecated(message = "This will be removed in upcoming release", level = DeprecationLevel.WARNING,
    replaceWith = ReplaceWith(
        "trySend(RouterContract.Inputs.GoBack())",
        "com.copperleaf.ballast.navigation.routing.RouterContract"
    )
)
fun <T: Route> Navigator<T>.navigateUp(){
    trySend(RouterContract.Inputs.GoBack())
}

/**
 * Pop up the backstack up to [popUpTo] before navigating to [destination]
 * @param destination Screen to navigate to
 * @param popUpTo: Screen to popup to
 * @param extras For more data to be passed, e.g query params or path params
 * For more information, see [more](https://copper-leaf.github.io/ballast/wiki/modules/ballast-navigation/#query-parameter-format).
 *
 */
@Deprecated(message = "This will be removed in upcoming release", level = DeprecationLevel.WARNING)
fun <T: Route> Navigator<T>.navigateWithPop(
    destination: T,
    popUpTo: T,
    extras: Destination.Directions<T>.() -> Destination.Directions<T> = { this }
){
    val route = destination.directions().extras().build()
    trySend(PopUpTo(route, popUpTo.directions().build()))
}

/**
 * Pop all backstack before navigating to destination
 * @param destination Screen to navigate to
 * @param extras: For more data to be passed, e.g query params or path params
 * For more information, see [more](https://copper-leaf.github.io/ballast/wiki/modules/ballast-navigation/#query-parameter-format).
 */
@Deprecated(message = "This will be removed in upcoming release", level = DeprecationLevel.WARNING)
fun <T: Route> Navigator<T>.navigateWithPopAll(
    destination: T,
    extras: Destination.Directions<T>.() -> Destination.Directions<T> = { this }
){
    val route = destination.directions().extras().build()
    trySend(PopUpAll(route))
}




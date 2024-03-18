package com.dilivva.ballastnavigationext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.eventHandler
import com.copperleaf.ballast.navigation.routing.Destination
import com.copperleaf.ballast.navigation.routing.Route
import com.copperleaf.ballast.navigation.routing.RouteAnnotation
import com.copperleaf.ballast.navigation.routing.RouteMatcher
import com.copperleaf.ballast.navigation.routing.RoutingTable
import com.copperleaf.ballast.navigation.routing.fromEnum
import com.copperleaf.ballast.navigation.vm.BasicRouter
import com.copperleaf.ballast.navigation.vm.Router
import com.copperleaf.ballast.navigation.vm.withRouter
import kotlinx.coroutines.CoroutineScope


/**
 * Extend this to create your destinations.
 * This was added to reduce boilerplate codes
 */
@Deprecated(message = "You should use the annotation based navigation", level = DeprecationLevel.WARNING)
interface BaseRoute: Route{
    val route: String
    override val annotations: Set<RouteAnnotation>
        get() = emptySet()
    override val matcher: RouteMatcher
        get() = RouteMatcher.create(route)
}


/**
 * Navigation controller
 * @param viewModelCoroutineScope Coroutine scope
 * @param screens List of destinations
 * @param initialScreen Callback for when the initial screen depends on a state.
 */
@Deprecated(message = "This will be removed in upcoming releases", level = DeprecationLevel.WARNING)
internal class NavigationController<SCREEN>(
    viewModelCoroutineScope: CoroutineScope,
    screens: Array<SCREEN>,
    initialScreen: () -> SCREEN
) : BasicRouter<SCREEN>(
    config = BallastViewModelConfiguration.Builder()
        .withRouter(RoutingTable.fromEnum(screens), initialScreen())
        .build(),
    eventHandler = eventHandler { },
    coroutineScope = viewModelCoroutineScope,
) where SCREEN : Enum<SCREEN>, SCREEN : Route


/**
 * Return a [Navigator] object created from [NavigationController].
 * @param S type of route
 * @param coroutineScope [CoroutineScope]
 * @param screens Array of screens
 * @param initialScreen callback for passing in the initial Screen (optional) if not passed it assumes that the first item
 * in the list is the initial screen.
 * @return [Router] of type S (Screen)
 */

@Composable
@Deprecated(message = "This will be removed in upcoming releases", level = DeprecationLevel.WARNING,
    replaceWith = ReplaceWith(
        "remember(coroutineScope) { NavigationController(coroutineScope, screens, initialScreen) }",
        "androidx.compose.runtime.remember",
        "com.dilivva.ballastnavigationext.Controller"
    )
)
fun <S> rememberNavigator(
    coroutineScope: CoroutineScope,
    screens: Array<S>,
    initialScreen: () -> S = { screens.first() }
): Navigator<S> where S : Enum<S>, S : Route{
    return remember(coroutineScope) {
        NavigationController(coroutineScope, screens, initialScreen)
    }
}

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

    fun matchRoute(match: Destination.Match<R>,appScreen: R): S


}
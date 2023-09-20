package com.dilivva.ballastnavigationext

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.copperleaf.ballast.navigation.routing.Backstack
import com.copperleaf.ballast.navigation.routing.Destination
import com.copperleaf.ballast.navigation.routing.Route
import com.copperleaf.ballast.navigation.routing.currentDestinationOrThrow
import com.copperleaf.ballast.navigation.routing.renderCurrentDestination
import com.copperleaf.ballast.navigation.vm.Router


typealias Navigator<T> = Router<T>

/**
 * Destination composable
 * @param T [Route]
 * @param navigator [Router] created from [rememberRouter]
 * @param onNavigate callback denoting a destination invoked
 * @param noDestination invoked when no route is found for a destination.
 * @param onDeviceBackPressed Optional, invoked when the device back navigation is called. Ignore if you
 * want it handled automatically. It automatically navigates to the previous screen if the backstack is greater
 * than 1 else exits the app. NOTE: This behaviour only applies on Android.
 * @param animateIn Enter animation for a screen. If not passed [defaultAnimateIn] is used.
 * @param animateOut Exit animation for a screen. If not passed [defaultAnimateOut] is used.
 */
@Composable
fun <T: Route> Destination(
    navigator: Navigator<T>,
    onNavigate: @Composable Destination.Match<T>.(T) -> Unit,
    noDestination: (String) -> Unit = {},
    onDeviceBackPressed: ((T) -> Unit)? = null,
    animateIn: AnimatedContentTransitionScope<T>.() -> ContentTransform = { defaultAnimateIn() },
    animateOut: AnimatedContentTransitionScope<T>.() -> ContentTransform = { defaultAnimateOut() }
){
    val routerState: Backstack<T> by navigator.observeStates().collectAsState()
    var destinations by remember { mutableStateOf(0) }
    val close = closeApp()
    val isEnterDirection by remember(routerState.size) {
        derivedStateOf {
            val result = routerState.size > destinations
            destinations = routerState.size
            result
        }
    }

    routerState.renderCurrentDestination(
        route = { appScreen: T ->
            AnimatedContent(
                targetState = appScreen,
                transitionSpec = {
                    if (isEnterDirection){
                        animateIn()
                    }else{
                        animateOut()
                    }
                }
            ) { state ->
                this@renderCurrentDestination.onNavigate(state)
            }

        },
        notFound = noDestination,
    )

    BackNavHandler {
        if (onDeviceBackPressed != null){
            val currentDestination = routerState.currentDestinationOrThrow.originalRoute
            onDeviceBackPressed(currentDestination)
        }else{
            if (routerState.size == 1){
                close.invoke()
                return@BackNavHandler
            }
            navigator.navigateUp()
        }
    }
}
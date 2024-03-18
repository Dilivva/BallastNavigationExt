package com.dilivva.ballastnavigationext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.copperleaf.ballast.navigation.routing.Destination
import com.copperleaf.ballast.navigation.routing.Route
import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.vm.Router
import kotlinx.coroutines.delay


/**
 * Composable function to handle device back button. PS needed only on android
 * @param onBack callback for when the device back button is invoked.
 */
@Composable
internal expect fun BackNavHandler(onBack: () -> Unit)


/**
 * Callback to close the app from Android
 */
@Composable
internal expect fun closeApp(): () -> Unit

@Composable
internal expect fun <T: Route> DeepLinkHandler(navigator: Router<T>)

@Composable
internal fun <T: Route> NavigateToDeeplink(navigator: Router<T>, destination: Destination<T>?){
    LaunchedEffect(destination){
        if (destination != null && destination is Destination.Match) {
            delay(50)
            navigator.trySend(RouterContract.Inputs.GoToDestination(destination.originalDestinationUrl))
        }
    }
}
package com.dilivva.ballastnavigationext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.copperleaf.ballast.navigation.routing.Route
import com.copperleaf.ballast.navigation.routing.UnmatchedDestination
import com.copperleaf.ballast.navigation.vm.Router

//Stub for device back handler as the app handles back navigation
@Composable
internal actual fun BackNavHandler(onBack: () -> Unit){}
//Stub
@Composable
internal actual fun closeApp(): () -> Unit {
    return { }
}


/**
 * Deep link result
 * Making use of Composition Locals to handle deep-linking on Ios
 */
internal var deepLinkResult by mutableStateOf(DeepLinkResult())
internal val LocalIosDeepLink = compositionLocalOf { deepLinkResult }

/**
 * Deep link result
 *
 * @property url
 * @constructor Create empty Deep link result
 */
data class DeepLinkResult(val url: String? = null){

    companion object{
        @Suppress("unused")
        fun setUrl(url: String){
            deepLinkResult = DeepLinkResult(url)
        }
    }
}

@Composable
internal actual fun <T: Route> DeepLinkHandler(navigator: Router<T>){
    CompositionLocalProvider(LocalIosDeepLink provides deepLinkResult) {
        val deepLinkResult = LocalIosDeepLink.current
        val router = navigator.observeStates().collectAsState().value
        val routingTable = router.routingTable
        val destination = remember(deepLinkResult) {
            if (deepLinkResult.url != null) routingTable.findMatch(UnmatchedDestination.parse(deepLinkResult.url))
            else null
        }
        NavigateToDeeplink(navigator, destination)
    }
}
package com.dilivva.ballastnavigationext

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.copperleaf.ballast.navigation.routing.Route
import com.copperleaf.ballast.navigation.routing.UnmatchedDestination

@Composable
internal actual fun BackNavHandler(onBack: () -> Unit) = BackHandler(onBack = onBack)

@Composable
internal actual fun closeApp(): () -> Unit{
    val context = LocalContext.current
    return {
        (context as Activity).finish()
    }
}

@Composable
internal actual fun <T: Route> DeepLinkHandler(navigator: Navigator<T>){
    val context = LocalContext.current
    val intent = (context as Activity).intent
    val uri: Uri? = intent.data
    val router = navigator.observeStates().collectAsState().value
    val routingTable = router.routingTable
    val destination = remember(uri) {
        if (uri != null) routingTable.findMatch(UnmatchedDestination.parse(uri.toString()))
        else null
    }
    NavigateToDeeplink(navigator,destination)

}
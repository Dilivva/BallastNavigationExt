package com.dilivva.ballastnavigationex.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.copperleaf.ballast.navigation.routing.optionalStringQuery
import com.dilivva.ballastnavigationex.presentation.HomeScreen
import com.dilivva.ballastnavigationex.presentation.auth.ForgotPasswordScreen
import com.dilivva.ballastnavigationex.presentation.auth.SignInScreen
import com.dilivva.ballastnavigationex.presentation.auth.SignUpScreen
import com.dilivva.ballastnavigationex.presentation.dashboard.DashboardScreen
import com.dilivva.ballastnavigationext.Destination
import com.dilivva.ballastnavigationext.closeApp
import com.dilivva.ballastnavigationext.navigateUp
import com.dilivva.ballastnavigationext.rememberRouter


@Composable
fun MainNavigation() {
    var isLoggedIn by remember { mutableStateOf(false) }
    val applicationScope = rememberCoroutineScope()
    val navigator = rememberRouter(applicationScope, MainRoute.values()){
        if (isLoggedIn) MainRoute.Dashboard else MainRoute.Home
    }
    val stopApp = closeApp()

    Destination(
        navigator = navigator,
        onNavigate = {
            when (it) {
                MainRoute.Home -> HomeScreen(navigator)
                MainRoute.SignIn -> SignInScreen(navigator){ isAuthed ->
                    isLoggedIn = isAuthed
                }
                MainRoute.SignUp -> SignUpScreen(navigator)
                MainRoute.ForgotPassword -> ForgotPasswordScreen(navigator)
                MainRoute.Dashboard -> {
                    //Passing data between screens
                    val email by optionalStringQuery("email")
                    DashboardScreen(email.orEmpty())
                }
                MainRoute.Profile -> {
                    Box(modifier = Modifier.fillMaxSize())
                }
            }
        },
        onDeviceBackPressed = {
            if (it == MainRoute.Dashboard){
                stopApp.invoke()
            }else{
                navigator.navigateUp()
            }
        }
    )

}
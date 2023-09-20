package com.dilivva.ballastnavigationext

import androidx.compose.runtime.Composable

@Composable
actual fun BackNavHandler(onBack: () -> Unit){
    //Stub for device back handler as the app handles back navigation
}

@Composable
actual fun closeApp(): () -> Unit{
    return {

    }
}
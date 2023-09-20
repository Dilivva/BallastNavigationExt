package com.dilivva.ballastnavigationext

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun BackNavHandler(onBack: () -> Unit) = BackHandler(onBack = onBack)

@Composable
actual fun closeApp(): () -> Unit{
    val context = LocalContext.current
    return {
        (context as Activity).finish()
    }
}
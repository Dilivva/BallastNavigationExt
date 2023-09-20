package com.dilivva.ballastnavigationext

import androidx.compose.runtime.Composable


/**
 * Composable function to handle device back button. PS needed only on android
 * @param onBack callback for when the device back button is invoked.
 */
@Composable
expect fun BackNavHandler(onBack: () -> Unit)

@Composable
expect fun closeApp(): () -> Unit
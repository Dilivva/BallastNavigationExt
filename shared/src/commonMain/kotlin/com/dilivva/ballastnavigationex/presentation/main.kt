package com.dilivva.ballastnavigationex.presentation

import androidx.compose.runtime.Composable
import com.dilivva.ballastnavigationex.presentation.navigation.MainNavigation
import com.dilivva.ballastnavigationex.presentation.theme.AppTheme

@Composable
fun App(){
    AppTheme {
        MainNavigation()
    }
}
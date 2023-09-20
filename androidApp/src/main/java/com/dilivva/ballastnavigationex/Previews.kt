package com.dilivva.ballastnavigationex

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dilivva.ballastnavigationex.presentation.dashboard.DashboardContent
import com.dilivva.ballastnavigationex.presentation.theme.AppTheme

@Composable
@Preview(
    device = "id:pixel_4_xl", showSystemUi = true,
    backgroundColor = 0xFFFFFFFF, showBackground = true
)
fun PreviewText(){
    AppTheme {
        DashboardContent()
    }
}
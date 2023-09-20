package com.dilivva.ballastnavigationex.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight


object Fonts {
    @Composable
    fun poppins() = FontFamily(
        font(
            "Poppins",
            "poppins_regular",
            FontWeight.Normal,
            FontStyle.Normal
        ),
        font(
            "Poppins",
            "poppins_italic",
            FontWeight.Normal,
            FontStyle.Italic
        ),

        font(
            "Poppins",
            "poppins_bold",
            FontWeight.Bold,
            FontStyle.Normal
        ),
        font(
            "Poppins",
            "poppins_bold_italic",
            FontWeight.Bold,
            FontStyle.Italic
        ),

        font(
            "Poppins",
            "poppins_extra_bold",
            FontWeight.ExtraBold,
            FontStyle.Normal
        ),
        font(
            "Poppins",
            "poppins_extra_bold_italic",
            FontWeight.ExtraBold,
            FontStyle.Italic
        ),

        font(
            "Poppins",
            "poppins_medium",
            FontWeight.Medium,
            FontStyle.Normal
        ),
        font(
            "Poppins",
            "poppins_medium_italic",
            FontWeight.Medium,
            FontStyle.Italic
        )
    )
}
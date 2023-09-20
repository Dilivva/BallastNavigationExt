package com.dilivva.ballastnavigationex.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dilivva.ballastnavigationex.presentation.theme.Fonts

@Composable
fun BneButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
){
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(60.dp),
        shape = RoundedCornerShape(11.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = ButtonDefaults.buttonElevation(0.dp,0.dp,0.dp,0.dp,0.dp)
    ){
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                fontFamily = Fonts.poppins()
            )
        )
    }
}
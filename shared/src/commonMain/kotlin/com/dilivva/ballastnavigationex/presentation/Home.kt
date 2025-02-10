package com.dilivva.ballastnavigationex.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dilivva.ballastnavigationex.presentation.components.BneButton
import com.dilivva.ballastnavigationex.presentation.theme.Fonts


@Composable
fun HomeScreen(){
    HomeContent{

    }
}

@Composable
fun HomeContent(
    onClick: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                imageVector = Icons.Filled.Wallet,
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)),
                modifier = Modifier.size(100.dp)
            )
            Text(
                "Cardy Pay",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Black,
                    fontSize = 35.sp
                ),
                fontFamily = Fonts.poppins()
            )
        }

        BneButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = "PROCEED",
            onClick = onClick
        )
    }
}
package com.dilivva.ballastnavigationex.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.West
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.dilivva.ballastnavigationex.presentation.components.BneButton

@Composable
fun ForgotPasswordScreen(

){
    ForgotPasswordContent(
        forgotPassWord = {},
        onBack = {  }
    )
}

@Composable
fun ForgotPasswordContent(
    forgotPassWord: (String) -> Unit,
    onBack: () -> Unit
){
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        IconButton(onClick = onBack, modifier = Modifier.align(Alignment.Start)){
            Icon(imageVector = Icons.Filled.West, contentDescription = null)
        }
        Spacer(modifier = Modifier.weight(1f))
        HelloText(text = "Reset Password!")
        WelcomeText(text = "Provide your email address to reset password!")
        Spacer(modifier = Modifier.weight(1f))
        BneTextField(
            text = email,
            icon = Icons.Filled.Email,
            label = "Email",
            placeholder = "Enter email",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = { email = it }
        )
        BneButton(text = "RESET PASSWORD", onClick = { forgotPassWord(email) })

        Spacer(modifier = Modifier.weight(4f))
    }
}
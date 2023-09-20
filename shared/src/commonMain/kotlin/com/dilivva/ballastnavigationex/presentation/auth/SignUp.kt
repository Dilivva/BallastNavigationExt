package com.dilivva.ballastnavigationex.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.copperleaf.ballast.navigation.vm.Router
import com.dilivva.ballastnavigationex.presentation.components.BneButton
import com.dilivva.ballastnavigationex.presentation.navigation.MainRoute
import com.dilivva.ballastnavigationext.navigateUp

@Composable
fun SignUpScreen(router: Router<MainRoute>){
    SignUpContent(
        signUp = { name, email, password -> },
        signIn = {},
        onBack = { router.navigateUp() }
    )

}

@Composable
fun SignUpContent(
    signUp: (String,String,String) -> Unit,
    signIn: () -> Unit,
    onBack: () -> Unit
){
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
        HelloText("First time?")
        WelcomeText("Create your account with few simple steps :)")
        Spacer(modifier = Modifier.weight(1f))
        BneTextField(
            text = fullName,
            icon = Icons.Filled.Person,
            label = "Full name",
            placeholder = "Enter full name",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, capitalization = KeyboardCapitalization.Words),
            onValueChange = { fullName = it }
        )
        BneTextField(
            text = email,
            icon = Icons.Filled.Email,
            label = "Email",
            placeholder = "Enter email",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = { email = it }
        )
        BneTextField(
            text = password,
            icon = Icons.Filled.Lock,
            label = "Password",
            placeholder = "Enter password",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = { password = it }
        )
        BneButton(text = "SIGNUP", onClick = { signUp(fullName,email,password) })
        CreateAccountButton(
            desc = "Already have an account? ",
            action = "Sign In",
            onClick = signIn
        )
        Spacer(modifier = Modifier.weight(4f))
    }
}
package com.dilivva.ballastnavigationex.presentation.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.West
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dilivva.ballastnavigationex.presentation.components.BneButton
import com.dilivva.ballastnavigationex.presentation.theme.Fonts

@Composable
fun SignInScreen(onLogged: (Boolean) -> Unit){
    SignInContent(
        createAccount = {  },
        forgotPassword = {  },
        signIn = {  email, password ->
            if (email.isNotEmpty() && password.isNotEmpty()){
                onLogged(true)
            }
        },
        onBack = { onLogged(false) }
    )
}

@Composable
 fun SignInContent(
    createAccount: () -> Unit,
    forgotPassword: () -> Unit,
    signIn: (String, String) -> Unit,
    onBack: () -> Unit
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(imageVector = Icons.Filled.West, contentDescription = null)
            }
            Spacer(modifier = Modifier.weight(1f))
            HelloText(text = "Hello Again!")
            WelcomeText(text = "Welcome back, you've been missed!")
            Spacer(modifier = Modifier.weight(1f))
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
            ForgotPasswordButton(modifier = Modifier.align(Alignment.End)) { forgotPassword() }
            BneButton(text = "SIGNIN", onClick = { signIn(email, password) })
            CreateAccountButton(
                desc = "Create a new Account? ",
                action = "Sign Up",
                onClick = createAccount
            )
            Spacer(modifier = Modifier.weight(4f))
        }
}

@Composable
fun HelloText(text: String){
    Text(
        text,
        fontFamily = Fonts.poppins(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp
    )
}
@Composable
fun WelcomeText(text: String){
    Text(
        text,
        fontFamily = Fonts.poppins(),
        fontWeight = FontWeight.W300,
        fontSize = 16.sp,
        modifier = Modifier.fillMaxWidth(0.5f),
        textAlign = TextAlign.Center
    )
}

@Composable
fun BneTextField(
    text: String,
    icon: ImageVector,
    label: String,
    placeholder: String = "",
    keyboardOptions: KeyboardOptions,
    onValueChange: (String) -> Unit
){
    OutlinedTextField(
        text,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        maxLines = 1,
        leadingIcon = { Icon( imageVector = icon, contentDescription = null) },
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(11.dp),
        singleLine = true
    )
}

@Composable
fun ForgotPasswordButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    TextButton(
        onClick = onClick,
        modifier = modifier,
        //colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onBackground)
    ){
        Text(
            "Forgot password?",
            fontFamily = Fonts.poppins()
        )
    }
}

@Composable
fun CreateAccountButton(
    modifier: Modifier = Modifier,
    desc: String,
    action: String,
    onClick: () -> Unit
){
    TextButton(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onBackground)
    ){
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontFamily = Fonts.poppins())){
                    append(desc)
                }
                withStyle(SpanStyle(fontFamily = Fonts.poppins(), fontWeight = FontWeight.Bold)){
                    append(action)
                }
            }
        )
    }
}


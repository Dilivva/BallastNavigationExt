package com.dilivva.ballastnavigationex.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.dilivva.ballastnavigationex.presentation.theme.TextFieldColorDark
import com.dilivva.ballastnavigationex.presentation.theme.TextFieldColorLight

@Composable
fun BneChatTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions =  KeyboardOptions.Default.copy(imeAction = ImeAction.Default),
    placeHolder: String = "",
    singleLine: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    decorator: @Composable ( @Composable () -> Unit ) -> Unit = {
        DefaultDecorator(
            value = value,
            enabled = enabled,
            placeHolder = placeHolder,
            singleLine = singleLine,
            innerTextField = it,
            trailingIcon = trailingIcon
        )
    }
){
    BasicTextField(
        value = value,
        onValueChange = onValueChanged,
        textStyle = MaterialTheme.typography.bodyMedium,
        modifier = modifier,
        keyboardOptions = keyboardOptions,
        enabled = enabled,
        decorationBox = @Composable { innerTextField ->
            decorator(innerTextField)
        },
        visualTransformation = visualTransformation
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DefaultDecorator(
    trailingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = bneTextFieldColor(),
    value: String,
    enabled: Boolean,
    placeHolder: String,
    singleLine: Boolean,
    innerTextField: @Composable () -> Unit
){

    TextFieldDefaults.DecorationBox(
        value = value,
        visualTransformation = VisualTransformation.None,
        innerTextField = innerTextField,
        enabled = enabled,
        placeholder = {
            Text(
                text = placeHolder,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        singleLine = singleLine,
        isError = false,
        trailingIcon = trailingIcon,
        interactionSource = remember { MutableInteractionSource() },
        colors = colors,
        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 15.dp),
        container = {
            val bg = if (isSystemInDarkTheme()) TextFieldColorDark else TextFieldColorLight
            Box(
                Modifier
                    .background(bg, shape = RoundedCornerShape(6.dp))
                    .border(
                        width = 0.7.dp,
                        color = MaterialTheme.colorScheme.primary.copy(0.2f),
                        shape = RoundedCornerShape(6.dp)
                    )
            )
        }
    )
}

@Composable
private fun bneTextFieldColor(): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(0.4f),
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface.copy(0.4f),
    )
}
package com.example.core.presentation.components.uikit

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.presentation.components.theme.StylishTheme

@Composable
fun StylishTextField(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String? = null,
    onValueChange: (String) -> Unit,
    unfocusedBorderColor: Color = StylishTextFieldDefaults.unfocusedBorderColor,
    containerColor: Color = StylishTextFieldDefaults.containerColor,
    @DrawableRes leadingIcon: Int? = null,
    @DrawableRes trailingIcon: Int? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    trailingIconClicked: () -> Unit = {},
    singleLine: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textStyle: TextStyle = LocalTextStyle.current,

    ) {
    OutlinedTextField(
        modifier = modifier,
        value = text,
        shape = StylishTextFieldDefaults.shape,
        textStyle = textStyle,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            unfocusedBorderColor = unfocusedBorderColor,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        singleLine = singleLine,
        onValueChange = onValueChange,
        placeholder = if (placeholder.isNullOrEmpty()) null else {
            {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodySmall,
                    color = StylishTextFieldDefaults.placeHolderColor,
                )
            }
        },
        leadingIcon = leadingIcon?.let {
            {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = leadingIcon),
                    contentDescription = "",
                    tint = StylishTextFieldDefaults.iconTint
                )

            }
        },
        trailingIcon = trailingIcon?.let {
            {
                IconButton(
                    modifier = Modifier.size(20.dp),
                    onClick = trailingIconClicked,
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = trailingIcon),
                        contentDescription = "",
                        tint = StylishTextFieldDefaults.iconTint
                    )
                }
            }
        },
    )
}


private object StylishTextFieldDefaults {
    val shape = RoundedCornerShape(size = 8.dp)
    val containerColor = Color(0xFFF3F3F3)
    val iconTint = Color(0xFF626262)
    val placeHolderColor = Color(0xFF676767)
    val unfocusedBorderColor = Color(0xFFA8A8A9)
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    StylishTheme {
        Surface(modifier = Modifier.padding(10.dp)) {
            StylishTextField(
                text = "",
                placeholder = "ju",
                onValueChange = {},
            )
        }
    }
}

package com.example.core.presentation.components.uikit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core.presentation.components.modifier.shadow

@Composable
fun StylishSearchTextField(
    modifier: Modifier = Modifier,
    text : String,
    placeHolderText : String? = null,
    onValueChange: (String) -> Unit,
    shapeDp : Dp = 7.dp,
    blurRadius : Dp = 10.dp,
    spread : Dp = 1.dp,
    color: Color,
    backgroundColor: Color,
) {

    OutlinedTextField(
        modifier = modifier
            .heightIn(min = 40.dp)
            .shadow(
                color = MaterialTheme.colorScheme.onBackground.copy(0.1f),
                borderRadius = shapeDp,
                blurRadius = blurRadius,
                spread = spread,
                offsetX = 1.dp,
                offsetY = 1.dp,
            )
            .clip(RoundedCornerShape(7.dp))
            .background(backgroundColor),
        shape = RoundedCornerShape(7.dp),
        value = text,
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge,
        placeholder = placeHolderText?.let{
            {
                Text(
                    text = placeHolderText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = color,
                )
            }
        },
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "",
                tint = color,
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
        )
    )
}
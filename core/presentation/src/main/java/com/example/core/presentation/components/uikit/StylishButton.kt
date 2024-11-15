package com.example.core.presentation.components.uikit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun StylishButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    icon: (@Composable () -> Unit)? = null,
    enabled: Boolean = true,
    color: Color = MaterialTheme.colorScheme.primary,
    shape: Shape = CircleShape,
    paddingValues: PaddingValues = PaddingValues(
        horizontal = 0.dp,
        vertical = 0.dp,
    ),
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(
                color = color,
                shape = shape
            )
            .clickable(
                enabled = enabled,
                onClick = onClick,
            )
            .padding(
                horizontal = 20.dp,
                vertical = 12.dp
            )
            .width(IntrinsicSize.Max)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues = paddingValues),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {

            if (icon != null) {
                icon.invoke()

                Spacer(modifier = Modifier.width(10.dp))
            }

            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = text,
                textAlign = TextAlign.Center,
                color = textColor,
                style = textStyle,
            )
        }
    }
}

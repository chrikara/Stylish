package com.example.core.presentation.components.uikit

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.presentation.components.uikit.StylishBulletIndicatorDefaults.selectedColor
import com.example.core.presentation.components.uikit.StylishBulletIndicatorDefaults.unselectedColor

@Composable
fun <T> StylishBulletIndicatorRow(
    modifier: Modifier = Modifier,
    options: List<T>,
    isSelected: (T) -> Boolean
) {
    Row(
        modifier = modifier.animateContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            alignment = Alignment.CenterHorizontally,
            space = 3.dp
        ),
    ) {
        options.forEach {
            StylishBulletIndicator(
                modifier = Modifier.offset(
                    y = if (isSelected(it)) (0.5).dp else 0.dp,
                ),
                isSelected = isSelected(it)
            )
        }

    }
}

@Composable
fun StylishBulletIndicator(
    modifier: Modifier = Modifier,
    isSelected: Boolean = true,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .size(if (isSelected) 9.dp else 8.dp)
            .background(
                if (isSelected) selectedColor else unselectedColor,
            )
    )

}

private object StylishBulletIndicatorDefaults {
    val selectedColor: Color = Color(0xFFFFA3B3)

    val unselectedColor: Color = Color(0xFFDEDBDB)
}


@Preview(showBackground = true)
@Composable
private fun Preview(modifier: Modifier = Modifier) {
    StylishBulletIndicatorRow(
        options = listOf(5),
        isSelected = { it == 2 }
    )
}

package com.example.products.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core.presentation.components.uikit.StylishButton
import com.example.products.presentation.R

@Composable
internal fun ErrorScreen(
    onRetryButtonClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag(stringResource(id = R.string.products_error_content)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(R.string.something_went_wrong_you_should_try_again))
        Spacer(modifier = Modifier.height(30.dp))
        StylishButton(
            text = stringResource(R.string.retry),
            onClick = onRetryButtonClicked,
        )
    }
}

package com.example.products.presentation.details.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.core.presentation.components.uikit.StylishTextField
import com.example.products.presentation.R

@Composable
fun DetailsEditSection(
    modifier: Modifier = Modifier,
    title: String,
    price: String,
    category: String,
    description: String,
    onTitleChanged: (String) -> Unit,
    onPriceChanged: (String) -> Unit,
    onCategoryChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 4.dp)
            .testTag(stringResource(R.string.details_edit_section_test_tag))
    ) {
        Spacer(modifier = Modifier.height(22.dp))

        StylishLabeledField(
            title = stringResource(R.string.title),
            text = title,
            onValueChange = onTitleChanged,
            testTagId = R.string.product_details_title,
        )
        Spacer(modifier = Modifier.height(18.dp))
        StylishLabeledField(
            title = stringResource(R.string.price),
            text = price,
            onValueChange = onPriceChanged,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            testTagId = R.string.product_details_price,
        )
        Spacer(modifier = Modifier.height(18.dp))
        StylishLabeledField(
            title = stringResource(R.string.category),
            text = category,
            onValueChange = onCategoryChanged,
            testTagId = R.string.product_details_category,
        )
        Spacer(modifier = Modifier.height(18.dp))
        StylishLabeledField(
            title = stringResource(R.string.description),
            text = description,
            onValueChange = onDescriptionChanged,
            singleLine = false,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default),
            testTagId = R.string.product_details_description,
        )

        Spacer(modifier = Modifier.height(18.dp))
    }
}


@Composable
private fun StylishLabeledField(
    title: String,
    text: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit,
    @StringRes testTagId: Int,
) {
    Text(
        text = title,
        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400)
    )

    Spacer(modifier = Modifier.height(15.dp))

    StylishTextField(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(stringResource(id = testTagId)),
        text = text,
        unfocusedBorderColor = Color(0xFFC8C8C8),
        containerColor = Color.White,
        onValueChange = onValueChange,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

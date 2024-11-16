package com.example.products.presentation.details.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.products.domain.model.Product
import com.example.products.presentation.R
import com.example.products.presentation.details.DetailsDefaults
import com.example.products.presentation.details.DetailsDefaults.DESCRIPTION_MAX_CHARACTERS

@Composable
internal fun DetailsBody(
    product: Product?,
) {
    Column(
        modifier = Modifier
            .animateContentSize()
            .testTag(stringResource(R.string.details_body_test_tag))
    ) {

        var isExpanded by remember {
            mutableStateOf(false)
        }

        val shouldShowMore by remember(product) {
            derivedStateOf {
                (product?.description?.length ?: 0) >= DESCRIPTION_MAX_CHARACTERS && !isExpanded
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        product?.title?.let { text ->
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        product?.price?.let { price ->
            Text(
                text = price.toString() + "€",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        product?.description?.let { description ->
            Text(
                text = stringResource(R.string.product_details),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.clickable(
                    enabled = description.length >= DESCRIPTION_MAX_CHARACTERS
                ) {
                    isExpanded = !isExpanded
                },
                text = buildAnnotatedString {
                    append(
                        if (isExpanded) description else description.take(
                            DESCRIPTION_MAX_CHARACTERS
                        )
                    )

                    if (shouldShowMore)
                        withStyle(style = SpanStyle(color = DetailsDefaults.seeMoreColor)) {
                            append(stringResource(id = R.string.see_more))
                        }
                },
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

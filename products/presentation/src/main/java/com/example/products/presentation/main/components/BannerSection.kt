package com.example.products.presentation.main.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.core.presentation.components.uikit.StylishBulletIndicatorRow
import com.example.products.domain.model.Category
import com.example.products.presentation.R
import com.example.products.presentation.main.ProductsMainDefaults
import com.example.products.presentation.main.textId
import java.util.Locale

@Composable
fun BannerCarousel(
    categories: List<Category>,
    pagerState: PagerState,
) {
    HorizontalPager(
        modifier = Modifier
            .testTag(stringResource(R.string.infinite_horizontal_pager_test_tag)),
        state = pagerState,
        pageSpacing = 16.dp,
    ) { page ->
        Banner(
            itemTitle = stringResource(id = categories[page].textId()).lowercase(Locale.getDefault()),
        )
    }
    Spacer(modifier = Modifier.height(16.dp))

    StylishBulletIndicatorRow(
        modifier = Modifier.fillMaxWidth(),
        options = categories,
        isSelected = { it == categories[pagerState.targetPage] }
    )

}

@Composable
fun Banner(
    itemTitle: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(contentColor = ProductsMainDefaults.bannerTextColor)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .background(brush = ProductsMainDefaults.bannerBackgroundBrush)
                .padding(
                    start = 14.dp,
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 40.dp)
                    .weight(0.6f)
            ) {
                Text(
                    text = stringResource(R.string._50_40_off),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.now_in, itemTitle),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = stringResource(R.string.all_colours),
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .border(
                            border = BorderStroke(
                                width = 1.dp,
                                color = ProductsMainDefaults.bannerTextColor,
                            ),
                            shape = RoundedCornerShape(6.dp)
                        )
                        .clickable { }
                        .padding(8.dp),
                    text = stringResource(R.string.shop_now),
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                )
            }

            Image(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight(),
                painter = painterResource(id = R.drawable.woman),
                contentDescription = "",
            )
        }
    }
}

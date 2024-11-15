package com.example.products.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.presentation.components.modifier.applyIf
import com.example.core.presentation.components.theme.StylishTheme
import com.example.core.presentation.components.uikit.StylishLogoImage
import com.example.core.presentation.components.uikit.StylishSearchTextField

@Composable
fun ProductsRoot(
    searchText: String = "",
    onSearchTextChanged: (String) -> Unit,
    categories: List<String> = listOf("electronics")
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { focusManager.clearFocus() }
            }
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        TopBar()
        Spacer(modifier = Modifier.height(16.dp))

        StylishSearchTextField(
            modifier = Modifier.fillMaxWidth(),
            text = searchText,
            onValueChange = onSearchTextChanged,
            placeHolderText = stringResource(R.string.search_any_product),
            color = ProductsPageDefaults.onBackground,
            backgroundColor = ProductsPageDefaults.background,
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = stringResource(R.string.all_featured),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(25.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) {
                CategoryItem(category = it)
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(contentColor = ProductsPageDefaults.bannerTextColor)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color(0xFFF1637C),
                                ProductsPageDefaults.bannerBackground
                            )
                        )
                    )
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
                        text = "50-40% OFF",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Now in smartphone",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(text = "All colours", style = MaterialTheme.typography.bodySmall)
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .border(
                                border = BorderStroke(
                                    1.dp,
                                    color = ProductsPageDefaults.bannerTextColor,
                                ),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(8.dp),
                        text = "Shop Now ->",
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
}

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.size(40.dp))


        StylishLogoImage(
            modifier = Modifier
                .size(
                    width = 112.dp,
                    height = 32.dp,
                ),
            resId = R.drawable.logo_products,
        )

        Image(
            modifier = Modifier.size(40.dp),
            painter = painterResource(id = R.drawable.ic_profile1),
            contentDescription = "",
        )
    }
}

@Composable
private fun CategoryItem(
    modifier: Modifier = Modifier,
    image: Int? = null,
    isSelected: Boolean = true,
    category: String,
) {
    val primary = MaterialTheme.colorScheme.primary

    Column(
        modifier = modifier
            .width(IntrinsicSize.Min),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier

                .size(56.dp)
                .applyIf(
                    enabled = isSelected,
                    modifier = Modifier.drawBehind {
                        drawCircle(
                            color = primary,
                            radius = size.minDimension / 2f + 3.dp.toPx(),
                            style = Stroke(width = 3.dp.toPx()),
                        )
                    }
                )

                .clip(CircleShape),
            painter =
            if (image != null)
                painterResource(id = image)
            else
                painterResource(id = R.drawable.ic_profile1),
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = category,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            overflow = TextOverflow.Ellipsis,
            maxLines = 3,
        )
    }
}


private object ProductsPageDefaults {
    val background = Color.White
    val onBackground = Color(0xFFBBBBBB)
    val bannerTextColor = Color(0xFFFFFFFF)
    val bannerBackground = Color(0xFFFFA3B3)
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    var searchText by remember {
        mutableStateOf("")
    }

    StylishTheme {

        ProductsRoot(
            searchText = searchText,
            onSearchTextChanged = { searchText = it },
            categories = listOf(
                "Electronics",
                "Woman's clothingss  clothingss st st st",
                "Electronics",
                "Electronics",
                "Jewelry",
                "Woman's clothingss  clothingss st st st"
            )
        )
    }

}



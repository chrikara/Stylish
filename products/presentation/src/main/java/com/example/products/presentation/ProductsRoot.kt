package com.example.products.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core.presentation.components.modifier.applyIf
import com.example.core.presentation.components.theme.StylishTheme
import com.example.core.presentation.components.uikit.StylishButton
import com.example.core.presentation.components.uikit.StylishLogoImage
import com.example.core.presentation.components.uikit.StylishSearchTextField
import com.example.products.domain.model.Category
import com.example.products.domain.model.Category.ELECTRONICS
import com.example.products.domain.model.Category.JEWELRY
import com.example.products.domain.model.Category.MENS_CLOTHING
import com.example.products.domain.model.Category.WOMENS_CLOTHING
import com.example.products.domain.model.Product


@Composable
fun ProductsRoot(
    viewModel: ProductsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val filteredProducts by viewModel.filteredProducts.collectAsStateWithLifecycle()
    val categorySelected by viewModel.categorySelected.collectAsStateWithLifecycle()
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()

    ProductsRoot(
        searchText = searchText,
        onSearchTextChanged = viewModel::onSearchTextChanged,
        onCategoryClicked = viewModel::onCategoryClicked,
        onProductClicked = {},
        products = filteredProducts,
        categories = categories,
        categorySelected = categorySelected,
        state = state,
        onClearClicked = viewModel::onClearClicked,
        onRetryButtonClicked = viewModel::getProductsAndCategories,
    )


}

@Composable
fun ProductsRoot(
    searchText: String = "",
    onSearchTextChanged: (String) -> Unit,
    categories: List<Category> = listOf(ELECTRONICS),
    onCategoryClicked: (Category) -> Unit,
    categorySelected: Category? = null,
    onProductClicked: (Product) -> Unit,
    products: List<Product> = listOf(),
    state: ProductsRootState,
    onClearClicked: () -> Unit = {},
    onRetryButtonClicked: () -> Unit,
) {
    when (state) {
        ProductsRootState.SUCCESS ->
            ProductsContent(
                searchText = searchText,
                onSearchTextChanged = onSearchTextChanged,
                onCategoryClicked = onCategoryClicked,
                onClearClicked = onClearClicked,
                onProductClicked = onProductClicked,
                products = products,
                categorySelected = categorySelected,
                categories = categories,
            )

        ProductsRootState.LOADING ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(50.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }

        ProductsRootState.ERROR ->
            Column(
                modifier = Modifier.fillMaxSize(),
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
}

@Composable
fun ProductsContent(
    searchText: String = "",
    onSearchTextChanged: (String) -> Unit,
    onClearClicked: () -> Unit = {},
    categories: List<Category> = listOf(Category.ELECTRONICS),
    onCategoryClicked: (Category) -> Unit,
    categorySelected: Category? = null,
    onProductClicked: (Product) -> Unit,
    products: List<Product> = listOf()
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
            onClearClicked = {
                focusManager.clearFocus()
                onClearClicked()
            },
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.padding(start = 6.dp),
            text = stringResource(R.string.all_featured),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(25.dp))

        if (categories.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(categories) {
                    CategoryItem(
                        category = it,
                        onCategoryClicked = onCategoryClicked,
                        isSelected = it == categorySelected
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))
        }

        /*
        Most of these string resources should probably come from backend, meaning the could be
        hoisted to the top composable for the viewModel to manage them. But since this is a demo and
        we don't have an actual API for this, we're adding them as string resources.
         */
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(contentColor = ProductsPageDefaults.bannerTextColor)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .background(brush = ProductsPageDefaults.bannerBackgroundBrush)
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
                        text = stringResource(R.string.now_in_smartphone),
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
                                    color = ProductsPageDefaults.bannerTextColor,
                                ),
                                shape = RoundedCornerShape(6.dp)
                            )
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

        Spacer(modifier = Modifier.height(50.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(products, key = { it.id }) {
                ProductItem(
                    modifier = Modifier.animateItem(),
                    product = it,
                    onClick = onProductClicked,
                )
            }
        }

        Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
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
    onCategoryClicked: (Category) -> Unit,
    category: Category,
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
                .clip(CircleShape)
                .clickable { onCategoryClicked(category) },
            painter =
            if (image != null)
                painterResource(id = image)
            else
                painterResource(id = R.drawable.ic_profile1),
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.height(7.dp))

        Text(
            text = stringResource(id = category.textId()),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            overflow = TextOverflow.Ellipsis,
            maxLines = 3,
        )
    }
}

@Composable
private fun ProductItem(
    modifier: Modifier = Modifier,
    product: Product,
    onClick: (Product) -> Unit,
) {
    Column(
        modifier =
        modifier
            .width(IntrinsicSize.Min)
            .clickable(onClick = { onClick(product) })
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(bottom = 4.dp)
                .width(142.dp)
                .height(110.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(data = product.image)
                .build(),
            contentDescription = stringResource(R.string.product),
            error = painterResource(R.drawable.ic_placeholder),
            placeholder = painterResource(R.drawable.ic_placeholder),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
        )

        Column(

        ) {
            product.title?.let {
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = it,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400),
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            product.price?.let {
                Text(
                    text = it.toString() + "€",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

    }
}

private object ProductsPageDefaults {
    val background = Color.White
    val onBackground = Color(0xFFBBBBBB)
    val bannerTextColor = Color(0xFFFFFFFF)
    val bannerBackground = Color(0xFFFFA3B3)
    val bannerBackgroundBrush = Brush.horizontalGradient(
        listOf(
            Color(0xFFF1637C),
            bannerBackground
        )
    )
}

internal fun Category.textId() = when (this) {
    MENS_CLOTHING -> R.string.men_s_clothing
    JEWELRY -> R.string.jewelry
    ELECTRONICS -> R.string.electronics
    WOMENS_CLOTHING -> R.string.women_s_clothing
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    var searchText by remember {
        mutableStateOf("")
    }

    val product = Product(
        id = 1,
        title = "IWC Schaffhausen\n" +
                "2021 Pilot's Watch \"SIHH 2019\" 44mm",
        price = 32.06,
        category = JEWELRY,
        image = "",
        description = ""
    )

    var categorySelected: Category? by remember {
        mutableStateOf(null)
    }

    val list = buildList { repeat(6) { add(product.copy(id = it)) } }

    StylishTheme {

        ProductsContent(
            searchText = searchText,
            onSearchTextChanged = { searchText = it },
            categories = listOf(
                Category.ELECTRONICS,
                Category.JEWELRY,
                Category.MENS_CLOTHING,
                Category.WOMENS_CLOTHING,
            ),
            products = list.mapIndexed { index, pr ->
                if (index == 1)
                    pr.copy(title = "asdasd")
                else
                    pr
            },
            categorySelected = categorySelected,
            onProductClicked = {},
            onCategoryClicked = { categorySelected = if (categorySelected == it) null else it },
        )
    }

}



package com.example.products.presentation.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
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
import com.example.core.presentation.components.ScreenState
import com.example.core.presentation.components.format.format
import com.example.core.presentation.components.modifier.applyIf
import com.example.core.presentation.components.modifier.clickableNoMergeNoRipple
import com.example.core.presentation.components.theme.StylishTheme
import com.example.core.presentation.components.uikit.StylishLogoImage
import com.example.core.presentation.components.uikit.StylishSearchTextField
import com.example.products.domain.model.Category
import com.example.products.domain.model.Category.ELECTRONICS
import com.example.products.domain.model.Category.JEWELRY
import com.example.products.domain.model.Category.MENS_CLOTHING
import com.example.products.domain.model.Category.WOMENS_CLOTHING
import com.example.products.domain.model.Product
import com.example.products.domain.model.uniqueKey
import com.example.products.presentation.R
import com.example.products.presentation.components.ErrorScreen
import com.example.products.presentation.components.LoadingScreen
import com.example.products.presentation.main.components.BannerCarousel


@Composable
fun ProductsMainRoot(
    viewModel: ProductsViewModel = hiltViewModel(),
    onProductClicked: (Product) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val filteredProducts by viewModel.filteredProducts.collectAsStateWithLifecycle()
    val categorySelected by viewModel.categorySelected.collectAsStateWithLifecycle()
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsState()

    ProductsMainRoot(
        searchText = searchText,
        onSearchTextChanged = viewModel::onSearchTextChanged,
        onCategoryClicked = viewModel::onCategoryClicked,
        onProductClicked = onProductClicked,
        products = filteredProducts,
        categories = categories,
        categorySelected = categorySelected,
        state = state,
        onClearClicked = viewModel::onClearClicked,
        onRetryButtonClicked = viewModel::getProductsAndCategories,
    )


}

@Composable
internal fun ProductsMainRoot(
    searchText: String = "",
    onSearchTextChanged: (String) -> Unit,
    categories: List<Category> = listOf(ELECTRONICS),
    onCategoryClicked: (Category) -> Unit,
    categorySelected: Category? = null,
    onProductClicked: (Product) -> Unit,
    products: List<Product> = listOf(),
    state: ScreenState,
    onClearClicked: () -> Unit = {},
    onRetryButtonClicked: () -> Unit,
) {
    when (state) {
        ScreenState.SUCCESS ->
            ProductsMainContent(
                searchText = searchText,
                onSearchTextChanged = onSearchTextChanged,
                onCategoryClicked = onCategoryClicked,
                onClearClicked = onClearClicked,
                onProductClicked = onProductClicked,
                products = products,
                categorySelected = categorySelected,
                categories = categories,
            )

        ScreenState.LOADING -> LoadingScreen()

        ScreenState.ERROR -> ErrorScreen(onRetryButtonClicked = onRetryButtonClicked)
    }
}

@Composable
private fun ProductsMainContent(
    searchText: String = "",
    onSearchTextChanged: (String) -> Unit,
    onClearClicked: () -> Unit = {},
    categories: List<Category> = listOf(ELECTRONICS),
    onCategoryClicked: (Category) -> Unit,
    categorySelected: Category? = null,
    onProductClicked: (Product) -> Unit,
    products: List<Product> = listOf()
) {
    val focusManager = LocalFocusManager.current
    val pagerState = rememberPagerState(pageCount = { categories.size })

    Column(
        modifier = Modifier
            .clickableNoMergeNoRipple {
                focusManager.clearFocus()
            }
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.background)
            .testTag(stringResource(id = R.string.products_main_content)),
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        TopBar()
        Spacer(modifier = Modifier.height(16.dp))

        StylishSearchTextField(
            modifier = Modifier.fillMaxWidth(),
            text = searchText,
            onValueChange = onSearchTextChanged,
            placeHolderText = stringResource(R.string.search_any_product),
            color = ProductsMainDefaults.onBackground,
            backgroundColor = ProductsMainDefaults.background,
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

        BannerCarousel(
            categories = categories,
            pagerState = pagerState,
        )

        Spacer(modifier = Modifier.height(50.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(products, key = Product::uniqueKey) {
                ProductItem(
                    modifier = Modifier.animateItem(),
                    product = it,
                    onClick = onProductClicked,
                )
            }
        }

        Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
    }
}

@Composable
private fun TopBar(modifier: Modifier = Modifier) {
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
            .width(IntrinsicSize.Min)
            .testTag(stringResource(R.string.category_item_test_tag)),
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
            .testTag(stringResource(R.string.product_item_test_tag))
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
                    text = it.format(),
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

    }
}

internal object ProductsMainDefaults {
    val background = Color.White
    val onBackground = Color(0xFFBBBBBB)
    val bannerTextColor = Color(0xFFFFFFFF)
    private val bannerBackground = Color(0xFFFFA3B3)
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

    val products = buildList { repeat(6) { add(product.copy(id = it)) } }

    StylishTheme {
        ProductsMainContent(
            searchText = searchText,
            onSearchTextChanged = { searchText = it },
            categories = listOf(
                ELECTRONICS,
                JEWELRY,
                MENS_CLOTHING,
                WOMENS_CLOTHING,
            ),
            products = products,
            categorySelected = categorySelected,
            onProductClicked = {},
            onCategoryClicked = { categorySelected = if (categorySelected == it) null else it },
        )
    }

}



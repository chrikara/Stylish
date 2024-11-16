package com.example.products.presentation.details

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core.presentation.components.ScreenState
import com.example.core.presentation.components.modifier.shadow
import com.example.core.presentation.components.theme.StylishTheme
import com.example.core.presentation.components.toast.showSingleToast
import com.example.core.presentation.components.uikit.StylishButton
import com.example.products.domain.model.Product
import com.example.products.presentation.R
import com.example.products.presentation.components.ErrorScreen
import com.example.products.presentation.components.LoadingScreen
import com.example.products.presentation.details.components.DetailsBody
import com.example.products.presentation.details.components.DetailsEditSection

@Composable
fun ProductDetailsRoot(
    viewModel: ProductDetailsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val state = viewModel.state
    val context = LocalContext.current

    ProductDetailsRoot(
        title = state.title,
        price = state.price,
        category = state.category,
        description = state.description,
        isRunning = state.isRunning,
        onBackClicked = onNavigateBack,
        onTitleChanged = viewModel::updateTitle,
        onPriceChanged = viewModel::updatePrice,
        onCategoryChanged = viewModel::updateCategory,
        onDescriptionChanged = viewModel::updateDescription,
        product = state.product,
        onSaveClicked = {
            viewModel.saveClicked(
                onSaveSuccessful = {
                    context.showSingleToast(R.string.save_was_successful)
                    onNavigateBack()
                },
                onSaveFailed = {
                    context.showSingleToast(R.string.could_not_save)
                }
            )
        },
        screenState = state.screenState,
        onRetryButtonClicked = viewModel::getProduct,
    )
}

@Composable
private fun ProductDetailsRoot(
    onBackClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    title: String,
    price: String,
    category: String,
    description: String,
    isRunning: Boolean,
    onTitleChanged: (String) -> Unit,
    onPriceChanged: (String) -> Unit,
    onCategoryChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    product: Product?,
    screenState: ScreenState,
    onRetryButtonClicked: () -> Unit,
) {
    when (screenState) {
        ScreenState.SUCCESS -> DetailsContent(
            onBackClicked = onBackClicked,
            onSaveClicked = onSaveClicked,
            title = title,
            price = price,
            category = category,
            description = description,
            isRunning = isRunning,
            onTitleChanged = onTitleChanged,
            onPriceChanged = onPriceChanged,
            onCategoryChanged = onCategoryChanged,
            onDescriptionChanged = onDescriptionChanged,
            product = product,
        )

        ScreenState.LOADING -> LoadingScreen()

        ScreenState.ERROR -> ErrorScreen(onRetryButtonClicked = onRetryButtonClicked)

    }
}


@Composable
fun DetailsContent(
    onBackClicked: () -> Unit = {},
    onSaveClicked: () -> Unit = {},
    title: String,
    price: String,
    category: String,
    description: String,
    isRunning: Boolean,
    onTitleChanged: (String) -> Unit,
    onPriceChanged: (String) -> Unit,
    onCategoryChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    product: Product? = null,

    ) {
    var isEditing by remember {
        mutableStateOf(false)
    }

    BackHandler(
        enabled = isEditing,
        onBack = { isEditing = false },
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                TopBar(
                    isEditing = isEditing,
                    onBackClick = { if (isEditing) isEditing = false else onBackClicked() },
                    onEditClick = { isEditing = true }
                )


                Spacer(modifier = Modifier.height(16.dp))

                ProductImage(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .fillMaxWidth()
                        .height(213.dp),
                    product = product,
                )

                AnimatedContent(
                    targetState = isEditing, label = ""
                ) { isEditEnabled ->
                    when (isEditEnabled) {
                        true -> DetailsEditSection(
                            modifier = Modifier.imePadding(),
                            title = title,
                            price = price,
                            category = category,
                            description = description,
                            onTitleChanged = onTitleChanged,
                            onPriceChanged = onPriceChanged,
                            onCategoryChanged = onCategoryChanged,
                            onDescriptionChanged = onDescriptionChanged,
                        )

                        else -> DetailsBody(product = product)
                    }
                }
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = isEditing,
                enter = slideInVertically(
                    animationSpec = spring(
                        dampingRatio = 0.4f,
                        stiffness = Spring.StiffnessLow
                    )
                ) { it },
                exit = slideOutVertically { it }
            ) {
                BottomBar(
                    modifier = Modifier
                        .shadow(
                            color = Color.Black.copy(alpha = 0.1f),
                            offsetY = (-1).dp,
                            blurRadius = 10.dp,
                        )
                        .background(DetailsDefaults.background)
                        .navigationBarsPadding(),
                    onSaveClicked = onSaveClicked,
                    isRunning = isRunning,
                )
            }
        }
    )
}

@Composable
private fun TopBar(
    isEditing: Boolean,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Icon(
            modifier = Modifier
                .clickable(onClick = onBackClick)
                .size(width = 9.dp, height = 20.dp),
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onBackground
        )

        if (!isEditing)
            Icon(
                modifier = Modifier
                    .clickable(onClick = onEditClick)
                    .size(20.dp),
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground
            )
    }
}


@Composable
private fun ProductImage(
    modifier: Modifier,
    product: Product?,
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(data = product?.image)
            .build(),
        contentDescription = stringResource(R.string.product),
        error = painterResource(R.drawable.ic_placeholder),
        placeholder = painterResource(R.drawable.ic_placeholder),
        contentScale = ContentScale.Fit,
        alignment = Alignment.Center,
    )
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    onSaveClicked: () -> Unit,
    isRunning: Boolean,
) {
    Box(
        modifier = modifier,
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth()
        )
        StylishButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 36.dp,
                    horizontal = DetailsDefaults.horizontalPadding
                ),
            text = stringResource(R.string.save),
            textStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
            onClick = onSaveClicked,
            enabled = !isRunning,
            icon = if (!isRunning) null else {
                {
                    CircularProgressIndicator(
                        modifier = Modifier.size(15.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            },
            shape = DetailsDefaults.buttonShape,
        )
    }
}

internal object DetailsDefaults {
    val buttonShape = RoundedCornerShape(8.dp)
    val background = Color(0xFFFDFDFD)
    val seeMoreColor = Color(0xFFFA7189)
    val horizontalPadding = 20.dp

    const val DESCRIPTION_MAX_CHARACTERS = 200
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    StylishTheme {
        val product = Product(
            id = 1,
            title = "Nike Sneakers",
            description = "bla bla blabla blablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablablabla blabla bla blabla bla blabla bla blabla bla bla ",
            price = 13.5,
            image = ""
        )
        DetailsContent(
            product = product,
            onTitleChanged = {},
            onCategoryChanged = {},
            onDescriptionChanged = {},
            onPriceChanged = {},
            onBackClicked = {},
            onSaveClicked = {},
            description = "",
            category = "",
            price = "",
            title = "",
            isRunning = false,
        )
    }
}

package com.example.products.presentation.main

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.test.core.app.ApplicationProvider
import com.example.core.commonTest.AndroidTest
import com.example.core.commonTest.hasTestTag
import com.example.core.commonTest.hasText
import com.example.core.presentation.components.ScreenState
import com.example.products.domain.model.Category
import com.example.products.domain.model.Product
import com.example.products.domain.model.fakeCategories
import com.example.products.domain.model.fakeProducts
import com.example.products.presentation.R
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.robolectric.annotation.Config

class ProductsMainRootTest : AndroidTest() {
    private val context = ApplicationProvider.getApplicationContext<Application>()

    @Test
    fun `when state is loading, assert only loading screen is present`() {
        // given
        val state = ScreenState.LOADING

        // when
        rule.setContent { ProductsMainRootUnderTest(state = state) }

        // then
        rule.onNode(hasTestTag(R.string.products_loading_content))
            .assertIsDisplayed()
    }

    @Test
    fun `when state is error, assert only error screen is present`() {
        // given
        val state = ScreenState.ERROR

        // when
        rule.setContent { ProductsMainRootUnderTest(state = state) }

        // then
        rule.onNode(hasTestTag(R.string.products_error_content))
            .assertIsDisplayed()
    }

    @Test
    fun `when state is error, button has correct click action`() {
        // given
        val state = ScreenState.ERROR
        val onClick: () -> Unit = mockk(relaxed = true)

        // when
        rule.setContent {
            ProductsMainRootUnderTest(
                state = state,
                onRetryButtonClicked = onClick,
            )
        }

        // then
        rule.onNode(hasClickAction())
            .performClick()

        verify {
            onClick()
        }
    }

    @Test
    fun `when state is success, main content is displayed`() {
        // given
        val state = ScreenState.SUCCESS

        // when
        rule.setContent { ProductsMainRootUnderTest(state = state) }

        // then
        rule.onNode(hasTestTag(R.string.products_main_content))
            .assertIsDisplayed()
    }

    @Test
    fun `when search text has focus, it loses it if we click elsewhere`() {
        // given
        val columnTestTag = "column test tag"

        // when
        rule.setContent {
            val focusManager = LocalFocusManager.current

            Column(
                modifier = Modifier
                    .testTag(columnTestTag)
                    .clickable {
                        focusManager.clearFocus()
                    },
            ) {
                ProductsMainRootUnderTest()
            }
        }

        rule.onNode(hasSetTextAction()).apply {
            performTextInput("sd")
            assertIsFocused()
        }
        rule.onNode(hasTestTag(columnTestTag)).performClick()


        // then
        rule.onNode(hasSetTextAction()).assertIsNotFocused()
    }

    @Test
    fun `when user types, clear button is visible and it perform correct click action`() {
        // given
        val text = "I typed this text"
        val onClick: () -> Unit = mockk(relaxed = true)

        // when
        rule.setContent {
            ProductsMainRootUnderTest(onClearClicked = onClick)
        }

        rule.onNode(hasTestTag(com.example.core.presentation.R.string.clear_icon_test_tag))
            .assertDoesNotExist()
        rule.onNode(hasSetTextAction()).performTextInput(text)
        rule.onNode(hasTestTag(com.example.core.presentation.R.string.clear_icon_test_tag))
            .assertIsDisplayed()
        rule.onNode(hasTestTag(com.example.core.presentation.R.string.clear_icon_test_tag))
            .performClick()

        // then
        verify {
            onClick()
        }
    }

    @Test
    fun `assert categories are not displayed if they're empty`() {
        // given
        val categories = emptyList<Category>()

        // when
        rule.setContent { ProductsMainRootUnderTest(categories = categories) }

        // then
        rule.onNode(hasTestTag(R.string.category_item_test_tag)).assertIsNotDisplayed()
    }

    @Test
    fun `assert category items are displayed with correct text`() {
        // when
        rule.setContent { ProductsMainRootUnderTest() }

        // then
        rule.onAllNodes(hasTestTag(R.string.category_item_test_tag)).assertCountEquals(
            fakeCategories.size
        )
        fakeCategories.forEach {
            rule.onNode(hasText(it.textId())).assertIsDisplayed()
        }
    }

    @Test
    fun `assert category has correct click action`() {
        // given
        val categoryClicked = fakeCategories[0]
        val onCategoryClicked: (Category) -> Unit = mockk(relaxed = true)

        // when
        rule.setContent { ProductsMainRootUnderTest(onCategoryClicked = onCategoryClicked) }

        rule.onAllNodes(hasTestTag(R.string.category_item_test_tag))[0]
            .performClick()

        // then
        verify {
            onCategoryClicked(categoryClicked)
        }
    }

    @Test
    @Config(qualifiers = "+w720dp-h1040dp")
    fun `assert product items are displayed with correct text`() {
        // when
        rule.setContent { ProductsMainRootUnderTest() }

        // then
        rule.onAllNodes(hasTestTag(R.string.product_item_test_tag)).assertCountEquals(
            fakeProducts.size
        )
        fakeProducts.forEach {
            rule.onNode(hasText(it.title!!)).assertIsDisplayed()
        }
    }

    @Test
    @Config(qualifiers = "+w720dp-h1040dp")
    fun `assert product has correct click action`() {
        // given
        val productClicked = fakeProducts[0]
        val onProductClicked: (Product) -> Unit = mockk(relaxed = true)

        // when
        rule.setContent { ProductsMainRootUnderTest(onProductClicked = onProductClicked) }

        rule.onAllNodes(hasTestTag(R.string.product_item_test_tag))[0]
            .performClick()

        // then
        verify {
            onProductClicked(productClicked)
        }
    }

    @Test
    fun `assert banner page is correctly moved`() {
        // given
        val firstCategoryText = context.getString(
            R.string.now_in,
            context.getString(fakeCategories[0].textId()).lowercase()
        )

        val secondCategoryTest = context.getString(
            R.string.now_in,
            context.getString(fakeCategories[1].textId()).lowercase()
        )

        // when
        rule.setContent { ProductsMainRootUnderTest(categories = fakeCategories) }
        rule.onNode(
            hasText(firstCategoryText)
        ).assertIsDisplayed()

        rule.onNode(hasTestTag(R.string.infinite_horizontal_pager_test_tag))
            .performTouchInput {
                swipeLeft()
            }

        // then
        rule.onNode(
            hasText(secondCategoryTest)
        ).assertIsDisplayed()
    }

    @Composable
    private fun ProductsMainRootUnderTest(
        onCategoryClicked: (Category) -> Unit = {},
        categorySelected: Category? = null,
        categories: List<Category> = fakeCategories,
        onProductClicked: (Product) -> Unit = {},
        state: ScreenState = ScreenState.SUCCESS,
        onClearClicked: () -> Unit = {},
        onRetryButtonClicked: () -> Unit = {},
    ) {
        var searchText by remember {
            mutableStateOf("")
        }

        ProductsMainRoot(
            searchText = searchText,
            onSearchTextChanged = { searchText = it },
            categories = categories,
            onCategoryClicked = onCategoryClicked,
            categorySelected = categorySelected,
            onProductClicked = onProductClicked,
            products = fakeProducts,
            state = state,
            onClearClicked = onClearClicked,
            onRetryButtonClicked = onRetryButtonClicked
        )
    }
}

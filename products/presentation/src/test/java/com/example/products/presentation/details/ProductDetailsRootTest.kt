package com.example.products.presentation.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.core.commonTest.AndroidTest
import com.example.core.commonTest.hasTestTag
import com.example.core.commonTest.hasText
import com.example.core.presentation.components.ScreenState
import com.example.products.domain.model.Product
import com.example.products.presentation.R
import com.example.products.presentation.main.fakeProducts
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import org.robolectric.annotation.Config

class ProductDetailsRootTest : AndroidTest() {
    @Test
    fun `when state is loading, assert only loading screen is present`() {
        // given
        val state = ScreenState.LOADING

        // when
        rule.setContent { ProductDetailsRootUnderTest(screenState = state) }

        // then
        rule.onNode(hasTestTag(R.string.products_loading_content))
            .assertIsDisplayed()
    }

    @Test
    fun `when state is error, assert only error screen is present`() {
        // given
        val state = ScreenState.ERROR

        // when
        rule.setContent { ProductDetailsRootUnderTest(screenState = state) }

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
            ProductDetailsRootUnderTest(
                screenState = state,
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
        rule.setContent { ProductDetailsRootUnderTest(screenState = state) }

        // then
        rule.onNode(hasTestTag(R.string.products_details_content_test_tag))
            .assertIsDisplayed()
    }

    @Test
    fun `when clicking back before pressing editing, click action is invoked`() {
        // given
        val onBackClicked: () -> Unit = mockk(relaxed = true)

        // when
        rule.setContent { ProductDetailsRootUnderTest(onBackClicked = onBackClicked) }
        rule.onNode(hasTestTag(R.string.back_icon_test_tag))
            .performClick()

        // then
        verify {
            onBackClicked()
        }
    }

    @Test
    fun `when clicking back after pressing editing, correct action happens back to back`() {
        // given
        val onBackClicked: () -> Unit = mockk(relaxed = true)

        // when
        rule.setContent { ProductDetailsRootUnderTest(onBackClicked = onBackClicked) }
        rule.onNode(hasTestTag(R.string.details_body_test_tag)).assertIsDisplayed()

        rule.onNode(hasTestTag(R.string.edit_icon_test_tag))
            .performClick()

        rule.onNode(hasTestTag(R.string.details_body_test_tag)).assertDoesNotExist()
        rule.onNode(hasTestTag(R.string.details_edit_section_test_tag)).assertIsDisplayed()

        rule.onNode(hasTestTag(R.string.back_icon_test_tag)).performClick()

        // then
        verify(exactly = 0) {
            onBackClicked()
        }
        rule.onNode(hasTestTag(R.string.details_edit_section_test_tag)).assertDoesNotExist()
        rule.onNode(hasTestTag(R.string.details_body_test_tag)).assertIsDisplayed()
    }

    @Test
    @Config(qualifiers = "+w720dp-h1040dp")
    fun `when clicking edit, assert necessary components are displayed with their given actions`() {
        // given
        val onTitleChanged: (String) -> Unit = mockk(relaxed = true)
        val onPriceChanged: (String) -> Unit = mockk(relaxed = true)
        val onCategoryChanged: (String) -> Unit = mockk(relaxed = true)
        val onDescriptionChanged: (String) -> Unit = mockk(relaxed = true)
        val title = "title"
        val price = "price"
        val category = "category"
        val description = "descrifption"

        // when
        rule.setContent {
            ProductDetailsRootUnderTest(
                onTitleChanged = onTitleChanged,
                onPriceChanged = onPriceChanged,
                onCategoryChanged = onCategoryChanged,
                onDescriptionChanged = onDescriptionChanged,
            )
        }

        rule.onNode(hasTestTag(R.string.edit_icon_test_tag))
            .performClick()
        rule.onNode(hasTestTag(R.string.product_details_title)).assertIsDisplayed()
        rule.onNode(hasTestTag(R.string.product_details_price)).assertIsDisplayed()
        rule.onNode(hasTestTag(R.string.product_details_category)).assertIsDisplayed()
        rule.onNode(hasTestTag(R.string.product_details_description)).assertIsDisplayed()

        rule.onNode(hasTestTag(R.string.product_details_title)).performTextInput(title)
        rule.onNode(hasTestTag(R.string.product_details_price)).performTextInput(price)
        rule.onNode(hasTestTag(R.string.product_details_category)).performTextInput(category)
        rule.onNode(hasTestTag(R.string.product_details_description)).performTextInput(description)

        // then
        verify(exactly = 1) {
            onTitleChanged(title)
            onPriceChanged(price)
            onCategoryChanged(category)
            onDescriptionChanged(description)
        }
    }

    @Test
    @Config(qualifiers = "+w720dp-h1040dp")
    fun `when clicking button, correct action is called`() {
        // given
        val onSaveClicked: () -> Unit = mockk(relaxed = true)

        // when
        rule.setContent { ProductDetailsRootUnderTest(onSaveClicked = onSaveClicked) }

        rule.onNode(hasTestTag(R.string.edit_icon_test_tag))
            .performClick()

        rule.onNode(hasText(R.string.save))
            .performClick()

        // then
        verify {
            onSaveClicked()
        }
    }


    @Composable
    fun ProductDetailsRootUnderTest(
        onBackClicked: () -> Unit = {},
        onSaveClicked: () -> Unit = {},
        onTitleChanged: (String) -> Unit = {},
        onPriceChanged: (String) -> Unit = {},
        onCategoryChanged: (String) -> Unit = {},
        onDescriptionChanged: (String) -> Unit = {},
        isRunning: Boolean = false,
        product: Product? = fakeProducts[0],
        screenState: ScreenState = ScreenState.SUCCESS,
        onRetryButtonClicked: () -> Unit = {},
    ) {
        ProductDetailsRoot(
            onBackClicked = onBackClicked,
            onSaveClicked = onSaveClicked,
            title = "",
            price = "",
            category = "",
            description = "",
            isRunning = isRunning,
            onTitleChanged = onTitleChanged,
            onPriceChanged = onPriceChanged,
            onCategoryChanged = onCategoryChanged,
            onDescriptionChanged = onDescriptionChanged,
            product = product,
            screenState = screenState,
            onRetryButtonClicked = onRetryButtonClicked,
        )
    }
}

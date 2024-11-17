package com.example.stylish.navigationroot

import android.app.Application
import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.core.presentation.components.Screen
import com.example.core.presentation.components.theme.StylishTheme
import com.example.core.presentation.components.toast.showSingleToast
import com.example.stylish.HiltComponentActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.kotest.matchers.shouldBe
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.core.presentation.R as uikitR
import com.example.login.presentation.R as loginR
import com.example.products.presentation.R as productsR

@HiltAndroidTest
class NavigationRootUiTest {
    private lateinit var navController: TestNavHostController
    private val context = ApplicationProvider.getApplicationContext<Application>()

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<HiltComponentActivity>().apply {
        mainClock.autoAdvance = false
    }

    @Before
    fun init() {
        mockkStatic(Context::showSingleToast)
        hiltRule.inject()
        composeRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            StylishTheme {
                NavigationRoot(navController = navController)
            }
        }
    }

    @Test
    fun end_to_end_test() {
        // splash
        navController.assertCurrentRouteName(Screen.Splash.toString())
        composeRule.mainClock.autoAdvance = true

        // login
        waitUntilNode(hasTestTag(loginR.string.username_text_field_test_tag))
        navController.assertCurrentRouteName(Screen.Login.toString())
        composeRule.onNode(hasTestTag(loginR.string.username_text_field_test_tag))
            .performTextInput("Username")

        composeRule.onNode(hasTestTag(loginR.string.password_text_field_test_tag))
            .performTextInput("Password")

        composeRule.onNode(hasTestTag(loginR.string.login_button_test_tag))
            .assertIsEnabled().performClick()

        // products main
        waitUntilNode(hasTestTag(uikitR.string.stylish_search_text_field_test_tag))

        navController.assertCurrentRouteName(Screen.Products.toString())

        composeRule.onNode(hasTestTag(uikitR.string.stylish_search_text_field_test_tag))
            .performTextInput("Shirt")
        composeRule.onAllNodes(hasTestTag(productsR.string.product_item_test_tag))[0].performClick()

        // products edit details
        waitUntilNode(hasTestTag(productsR.string.details_body_test_tag))
        navController.assertCurrentRouteName(Screen.Edit::class.qualifiedName!! + "/{id}")

        composeRule.onNode(hasTestTag(productsR.string.edit_icon_test_tag))
            .performClick()

        // product edit body
        composeRule.onNode(hasTestTag(productsR.string.product_details_title))
            .performTextInput("Title")
        composeRule.onNode(hasTestTag(productsR.string.product_details_price))
            .performTextInput("Price")
        composeRule.onNode(hasTestTag(productsR.string.product_details_category))
            .performTextInput("Category")
        composeRule.onNode(hasTestTag(productsR.string.product_details_description))
            .performTextInput("Description")

        composeRule.onNode(hasTestTag(productsR.string.save_button_test_tag))
            .performClick()

        // products main
        waitUntilNode(hasTestTag(uikitR.string.stylish_search_text_field_test_tag))

        navController.assertCurrentRouteName(Screen.Products.toString())

        verify {
            any<Context>().showSingleToast(loginR.string.login_was_successful)
            any<Context>().showSingleToast(productsR.string.save_was_successful)
        }
    }

    private fun hasTestTag(
        @StringRes testTagId: Int,
    ): SemanticsMatcher = androidx.compose.ui.test.hasTestTag(
        testTag = context.getString(testTagId)
    )

    private fun waitUntilNode(node: SemanticsMatcher) {
        composeRule.waitUntil(timeoutMillis = 10000) {
            composeRule.onAllNodes(node)
                .fetchSemanticsNodes().size == 1
        }
    }

    private fun NavController.assertCurrentRouteName(expectedRouteName: String) {
        currentBackStackEntry?.destination?.route shouldBe expectedRouteName
    }
}


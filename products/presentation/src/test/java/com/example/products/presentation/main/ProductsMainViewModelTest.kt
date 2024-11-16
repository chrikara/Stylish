package com.example.products.presentation.main

import app.cash.turbine.test
import com.example.core.commonTest.MainDispatcherRule
import com.example.core.commonTest.testFirst
import com.example.core.data.di.DispatchersProvider
import com.example.core.data.di.TestStylishDispatchersProvider
import com.example.core.presentation.components.ScreenState
import com.example.products.domain.ProductsRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ProductsMainViewModelTest {
    private lateinit var repository: ProductsRepository
    private lateinit var viewModel: ProductsViewModel
    private val testDispatcher: DispatchersProvider = TestStylishDispatchersProvider()

    @get:Rule
    val rule = MainDispatcherRule()

    @Test
    fun `screen state is correct if product request is success`() = runTest {
        // given
        repository = mockk(relaxed = true) {
            coEvery { getProducts() } returns Result.success(mockk(relaxed = true))
        }

        // when
        viewModel = ProductsViewModel(repository, testDispatcher)

        // then
        viewModel.state.test {
            awaitItem() shouldBe ScreenState.LOADING
            awaitItem() shouldBe ScreenState.SUCCESS
        }
    }

    @Test
    fun `screen state is correct if product request is error`() = runTest {
        // given
        repository = mockk(relaxed = true) {
            coEvery { getProducts() } returns Result.failure(RuntimeException())
        }

        // when
        viewModel = ProductsViewModel(repository, testDispatcher)

        // then
        viewModel.state.test {
            awaitItem() shouldBe ScreenState.LOADING
            awaitItem() shouldBe ScreenState.ERROR
        }
    }

    @Test
    fun `correctly updates products when products request is success`() = runTest {
        // given
        val products = fakeProducts
        repository = mockk(relaxed = true) {
            coEvery { getProducts() } returns Result.success(products)
            coEvery { getCategories() } returns Result.success(fakeCategories)
        }

        // when
        viewModel = ProductsViewModel(repository, testDispatcher)
        advanceUntilIdle()

        // then
        viewModel.products.value shouldBe products
        viewModel.filteredProducts.testFirst() shouldBe products
    }

    @Test
    fun `correctly updates categories when categories request is success`() = runTest {
        // given
        val categories = fakeCategories
        repository = mockk(relaxed = true) {
            coEvery { getProducts() } returns Result.success(fakeProducts)
            coEvery { getCategories() } returns Result.success(categories)
        }

        // when
        viewModel = ProductsViewModel(repository, testDispatcher)
        advanceUntilIdle()

        // then
        viewModel.categories.value shouldBe categories
    }

    @Test
    fun `set categories to empty list when categories request is failure`() = runTest {
        // given
        repository = mockk(relaxed = true) {
            coEvery { getProducts() } returns Result.success(fakeProducts)
            coEvery { getCategories() } returns Result.failure(Exception())
        }

        // when
        viewModel = ProductsViewModel(repository, testDispatcher)
        advanceUntilIdle()

        // then
        viewModel.categories.value shouldBe emptyList()
    }

    @Test
    fun `onCategoryClicked - when category clicked, update relevant value`() = runTest {
        // given
        val categoryClicked = fakeCategories[0]
        repository = mockk(relaxed = true) {
            coEvery { getProducts() } returns Result.success(fakeProducts)
            coEvery { getCategories() } returns Result.success(fakeCategories)
        }

        // when
        viewModel = ProductsViewModel(repository, testDispatcher)
        advanceUntilIdle()
        viewModel.onCategoryClicked(categoryClicked)

        // then
        viewModel.categorySelected.value shouldBe categoryClicked
    }

    @Test
    fun `onCategoryClicked - when same category is clicked, set null to relevante value`() =
        runTest {
            // given
            val categoryClicked = fakeCategories[0]
            repository = mockk(relaxed = true) {
                coEvery { getProducts() } returns Result.success(fakeProducts)
                coEvery { getCategories() } returns Result.success(fakeCategories)
            }

            // when
            viewModel = ProductsViewModel(repository, testDispatcher)
            advanceUntilIdle()
            viewModel.onCategoryClicked(categoryClicked)
            viewModel.onCategoryClicked(categoryClicked)


            // then
            viewModel.categorySelected.value shouldBe null
        }

    @Test
    fun `onCategoryClicked - when new category is clicked, update to new value`() =
        runTest {
            // given
            val firstCategoryClicked = fakeCategories[0]
            val secondCategoryClicked = fakeCategories[1]
            repository = mockk(relaxed = true) {
                coEvery { getProducts() } returns Result.success(fakeProducts)
                coEvery { getCategories() } returns Result.success(fakeCategories)
            }

            // when
            viewModel = ProductsViewModel(repository, testDispatcher)
            advanceUntilIdle()
            viewModel.onCategoryClicked(firstCategoryClicked)
            viewModel.onCategoryClicked(secondCategoryClicked)

            // then
            viewModel.categorySelected.value shouldBe secondCategoryClicked
        }

    @Test
    fun `search text updates correctly and clearing text performs as intended`() = runTest {
        // given
        val text = "jewel"
        repository = mockk(relaxed = true) {
            coEvery { getProducts() } returns Result.success(fakeProducts)
            coEvery { getCategories() } returns Result.success(fakeCategories)
        }

        // when
        viewModel = ProductsViewModel(repository, testDispatcher)
        advanceUntilIdle()
        viewModel.onSearchTextChanged(text)
        viewModel.searchText.value shouldBe text
        viewModel.onClearClicked()

        // then
        viewModel.searchText.value shouldBe ""
    }

    @Test
    fun `when only updating search text, filter products correctly`() = runTest {
        // given
        val text = "Casual"
        repository = mockk(relaxed = true) {
            coEvery { getProducts() } returns Result.success(fakeProducts)
            coEvery { getCategories() } returns Result.success(fakeCategories)
        }

        // when
        viewModel = ProductsViewModel(repository, testDispatcher)
        advanceUntilIdle()
        viewModel.onSearchTextChanged(text)

        // then
        viewModel.filteredProducts.testFirst() shouldBe fakeProducts.filter {
            it.title?.contains(text, ignoreCase = true) == true
        }
    }

    @Test
    fun `when only updating category, filter products correctly`() = runTest {
        // given
        val categoryClicked = fakeCategories[0]
        repository = mockk(relaxed = true) {
            coEvery { getProducts() } returns Result.success(fakeProducts)
            coEvery { getCategories() } returns Result.success(fakeCategories)
        }

        // when
        viewModel = ProductsViewModel(repository, testDispatcher)
        advanceUntilIdle()
        viewModel.onCategoryClicked(categoryClicked)

        // then
        viewModel.filteredProducts.testFirst() shouldBe fakeProducts.filter {
            it.category == categoryClicked
        }
    }

    @Test
    fun `when updating category & search text, filter products correctly`() = runTest {
        // given
        val text = "el"
        val categoryClicked = fakeCategories[0]
        repository = mockk(relaxed = true) {
            coEvery { getProducts() } returns Result.success(fakeProducts)
            coEvery { getCategories() } returns Result.success(fakeCategories)
        }

        // when
        viewModel = ProductsViewModel(repository, testDispatcher)
        advanceUntilIdle()
        viewModel.onCategoryClicked(categoryClicked)
        viewModel.onSearchTextChanged(text)

        // then
        viewModel.filteredProducts.testFirst() shouldBe fakeProducts.filter {
            it.category == categoryClicked && (it.title?.contains(text, ignoreCase = true) == true)
        }
    }
}














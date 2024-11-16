package com.example.products.presentation.details

import androidx.lifecycle.SavedStateHandle
import com.example.core.commonTest.AndroidTest
import com.example.core.presentation.components.ScreenState
import com.example.products.domain.ProductsRepository
import com.example.products.domain.model.Product
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ProductsDetailsViewModelTest : AndroidTest() {
    private lateinit var repository: ProductsRepository
    private lateinit var viewModel: ProductDetailsViewModel
    private val id = 1
    private val savedStateHandle: SavedStateHandle = SavedStateHandle(mapOf("id" to id))

    @Test
    fun `when result is success && product not null, screen state is success`() = runTest {
        // given
        val product = mockk<Product>()
        repository = mockk {
            coEvery { getProduct(id = id) } returns Result.success(product)
        }

        // when
        viewModel = ProductDetailsViewModel(
            savedStateHandle = savedStateHandle,
            repository = repository,
        )

        // then
        viewModel.state.screenState shouldBe ScreenState.SUCCESS
        viewModel.state.product shouldBe product
    }

    @Test
    fun `when result is failure, screen state is error`() = runTest {
        // given
        repository = mockk {
            coEvery { getProduct(id = id) } returns Result.failure(Exception())
        }

        // when
        viewModel = ProductDetailsViewModel(
            savedStateHandle = savedStateHandle,
            repository = repository,
        )


        // then
        viewModel.state.screenState shouldBe ScreenState.ERROR
    }

    @Test
    fun `savedClicked - when successfully updates product, calls necessary action`() = runTest {
        // given
        val onSaveSuccessful: () -> Unit = mockk(relaxed = true)
        val product = mockk<Product>()
        repository = mockk {
            coEvery { getProduct(id = id) } returns Result.success(product)
            coEvery { updateProduct(id = id, any(), any(), any(), any()) } returns Result.success(
                Unit
            )
        }

        // when
        viewModel = ProductDetailsViewModel(
            savedStateHandle = savedStateHandle,
            repository = repository,
        )
        viewModel.saveClicked(
            onSaveSuccessful = onSaveSuccessful,
            onSaveFailed = {},
        )

        // then
        coVerify {
            repository.updateProduct(id, any(), any(), any(), any())
        }
        verify {
            onSaveSuccessful()
        }
        viewModel.state.isRunning shouldBe true
    }

    @Test
    fun `savedClicked - when unsuccessfully updates product, calls necessary action`() = runTest {
        // given
        val onSaveFailed: () -> Unit = mockk(relaxed = true)
        repository = mockk {
            coEvery { getProduct(id = id) } returns Result.success(mockk<Product>())
            coEvery { updateProduct(id = id, any(), any(), any(), any()) } returns Result.failure(
                Exception()
            )
        }

        // when
        viewModel = ProductDetailsViewModel(
            savedStateHandle = savedStateHandle,
            repository = repository,
        )
        viewModel.saveClicked(
            onSaveSuccessful = {},
            onSaveFailed = onSaveFailed,
        )

        // then
        coVerify {
            repository.updateProduct(id, any(), any(), any(), any())
        }
        verify {
            onSaveFailed()
        }
        viewModel.state.isRunning shouldBe false
    }

    @Test
    fun `updates all fields appropriately`() = runTest {
        // given
        val title = "title"
        val price = "price"
        val category = "category"
        val description = "description"
        repository = mockk {
            coEvery { getProduct(id = id) } returns Result.success(mockk<Product>())
            coEvery { updateProduct(id = id, any(), any(), any(), any()) } returns Result.success(
                Unit
            )
        }

        // when
        viewModel = ProductDetailsViewModel(
            savedStateHandle = savedStateHandle,
            repository = repository,
        )
        viewModel.updateTitle(title)
        viewModel.updatePrice(price)
        viewModel.updateCategory(category)
        viewModel.updateDescription(description)

        // then
        viewModel.state.title shouldBe title
        viewModel.state.price shouldBe price
        viewModel.state.category shouldBe category
        viewModel.state.description shouldBe description
    }
}

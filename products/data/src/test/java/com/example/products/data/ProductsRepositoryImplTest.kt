package com.example.products.data

import com.example.core.network.NetworkTest
import com.example.products.data.mappers.CategoryResponseMapper
import com.example.products.data.mappers.ProductResponseMapper
import com.example.products.domain.model.Category
import com.example.products.domain.model.Product
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ProductsRepositoryImplTest : NetworkTest() {
    @Test
    fun `getProducts - call repository, correct body is set`() = runTest {
        // when
        val mockEngine = getHttpClientEngine(listOf(ProductResponse(1)))
        val repository = ProductsRepositoryImpl(
            httpClientEngine = mockEngine
        )

        repository.getProducts()

        // then
        request.shouldNotBeNull()
        request?.method?.value shouldBe "GET"
        request?.url?.host shouldBe "fakestoreapi.com"
        request?.url?.encodedPath shouldBe "/products"
    }

    @Test
    fun `getProduct - call repository, correct body is set`() = runTest {
        // when
        val id = 1
        val mockEngine = getHttpClientEngine(mockk<ProductResponse>(relaxed = true))
        val repository = ProductsRepositoryImpl(
            httpClientEngine = mockEngine
        )

        repository.getProduct(id = 1)

        // then
        request.shouldNotBeNull()
        request?.method?.value shouldBe "GET"
        request?.url?.host shouldBe "fakestoreapi.com"
        request?.url?.encodedPath shouldBe "/products/$id"
    }

    @Test
    fun `updateProduct - call repository, correct body is set`() = runTest {
        // when
        val id = 1
        val mockEngine = getHttpClientEngine(Unit)
        val repository = ProductsRepositoryImpl(
            httpClientEngine = mockEngine
        )

        repository.updateProduct(id = id, "", 0.0, "", "")

        // then
        request.shouldNotBeNull()
        request?.method?.value shouldBe "PUT"
        request?.url?.host shouldBe "fakestoreapi.com"
        request?.url?.encodedPath shouldBe "/products/$id"
    }

    @Test
    fun `getCategories - call repository, correct body is set`() = runTest {
        // when
        val mockEngine = getHttpClientEngine(mockk<List<Category>>(relaxed = true))
        val repository = ProductsRepositoryImpl(
            httpClientEngine = mockEngine
        )

        repository.getCategories()

        // then
        request.shouldNotBeNull()
        request?.method?.value shouldBe "GET"
        request?.url?.host shouldBe "fakestoreapi.com"
        request?.url?.encodedPath shouldBe "/products/categories"
    }

    @Test
    fun `getProducts - when request is successful, correct result is returned`() = runTest {
        // given
        val product = mockk<Product>(relaxed = true)
        val mockEngine = getHttpClientEngine(listOf(ProductResponse(1)))
        val productResponseMapper = mockk<ProductResponseMapper> {
            every { any<ProductResponse>().toProduct() } returns product
        }

        // when
        val repository = ProductsRepositoryImpl(
            httpClientEngine = mockEngine,
            productResponseMapper = productResponseMapper
        )

        val result = repository.getProducts()

        // then
        result.isSuccess shouldBe true
        result.getOrNull() shouldBe listOf(product)
    }

    @Test
    fun `getProducts - when request is unsuccessful, correct result is returned`() = runTest {
        // given
        val product = mockk<Product>(relaxed = true)
        val mockEngine = getHttpClientEngine(listOf(ProductResponse(1)), isSuccessful = false)
        val productResponseMapper = mockk<ProductResponseMapper> {
            every { any<ProductResponse>().toProduct() } returns product
        }

        // when
        val repository = ProductsRepositoryImpl(
            httpClientEngine = mockEngine,
            productResponseMapper = productResponseMapper
        )

        val result = repository.getProducts()

        // then
        result.isSuccess shouldBe false
        result.getOrNull() shouldBe null
    }

    @Test
    fun `getProduct - when request is successful, correct result is returned`() = runTest {
        // given
        val product = mockk<Product>(relaxed = true)
        val mockEngine = getHttpClientEngine(mockk<ProductResponse>(relaxed = true))
        val productResponseMapper = mockk<ProductResponseMapper> {
            every { any<ProductResponse>().toProduct() } returns product
        }

        // when
        val repository = ProductsRepositoryImpl(
            httpClientEngine = mockEngine,
            productResponseMapper = productResponseMapper
        )

        val result = repository.getProduct(1)

        // then
        result.isSuccess shouldBe true
        result.getOrNull() shouldBe product
    }

    @Test
    fun `getProduct - when request is unsuccessful, correct result is returned`() = runTest {
        // given
        val product = mockk<Product>(relaxed = true)
        val mockEngine = getHttpClientEngine(mockk<ProductResponse>(relaxed = true), isSuccessful = false)
        val productResponseMapper = mockk<ProductResponseMapper> {
            every { any<ProductResponse>().toProduct() } returns product
        }

        // when
        val repository = ProductsRepositoryImpl(
            httpClientEngine = mockEngine,
            productResponseMapper = productResponseMapper,
        )

        val result = repository.getProduct(1)

        // then
        result.isSuccess shouldBe false
        result.getOrNull() shouldBe null
    }

    @Test
    fun `getCategories - when request is successful, correct result is returned`() = runTest {
        // given
        val categories = listOf(Category.JEWELRY)
        val mockEngine = getHttpClientEngine(mockk<List<Category>>(relaxed = true))
        val categoryResponseMapper = mockk<CategoryResponseMapper> {
            every { any<List<String>>().toCategories() } returns categories
        }

        // when
        val repository = ProductsRepositoryImpl(
            httpClientEngine = mockEngine,
            categoryResponseMapper = categoryResponseMapper
        )

        val result = repository.getCategories()

        // then
        result.isSuccess shouldBe true
        result.getOrNull() shouldBe categories
    }

    @Test
    fun `getCategories - when request is unsuccessful, correct result is returned`() = runTest {
        // given
        val categories = listOf(Category.JEWELRY)
        val mockEngine = getHttpClientEngine(mockk<List<Category>>(relaxed = true), isSuccessful = false)
        val categoryResponseMapper = mockk<CategoryResponseMapper> {
            every { any<List<String>>().toCategories() } returns categories
        }

        // when
        val repository = ProductsRepositoryImpl(
            httpClientEngine = mockEngine,
            categoryResponseMapper = categoryResponseMapper
        )

        val result = repository.getCategories()

        // then
        result.isSuccess shouldBe false
        result.getOrNull() shouldBe null
    }
}

package com.example.products.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.products.domain.ProductsRepository
import com.example.products.domain.model.Category
import com.example.products.domain.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductsRepository,
) : ViewModel() {

    private var allProducts = emptyList<Product>()

    private var _state = MutableStateFlow(ProductsRootState.LOADING)
    val state = _state.asStateFlow()

    private var _products = MutableStateFlow(emptyList<Product>())
    val products = _products.asStateFlow()

    private var _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private var _categorySelected = MutableStateFlow(null as Category?)
    val categorySelected = _categorySelected.asStateFlow()

    var _categories = MutableStateFlow(emptyList<Category>())
    val categories = _categories.asStateFlow()

    init {
        getProductsAndCategories()

    }

    val filteredProducts = combine(
        searchText, categorySelected, products
    ) { searchText, categorySelected, _ ->
        when {
            searchText.isBlank() && categorySelected == null -> allProducts
            else -> {
                allProducts.filter { product ->
                    // Check if the product matches the search text
                    val matchesSearchText = searchText.isBlank() || product.title?.contains(
                        searchText,
                        ignoreCase = true
                    ) == true

                    // Check if the product matches any selected category
                    val matchesCategory =
                        categorySelected == null || product.category == categorySelected

                    // Include the product if it matches both filters
                    matchesSearchText && matchesCategory
                }
            }
        }
    }
        .flowOn(Dispatchers.Default)
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun getProductsAndCategories() = viewModelScope.launch {
        _state.update { ProductsRootState.LOADING }

        val productsResult = async { repository.getProducts() }
        val categoriesResult = async { repository.getCategories() }

        productsResult.await().onSuccess { products ->
            _state.update { ProductsRootState.SUCCESS }
            allProducts = products.sortedBy { it.title }
            _products.update { allProducts }
        }.onFailure {
            _state.update { ProductsRootState.ERROR }
        }

        _categories.value = categoriesResult.await().getOrElse { emptyList() }
    }

    fun onCategoryClicked(category: Category) {
        _categorySelected.value = if (_categorySelected.value != category)
            category
        else
            null
    }

    fun onSearchTextChanged(text: String) {
        _searchText.update { text }
    }

    fun onClearClicked() {
        _searchText.update { "" }
    }
}

enum class ProductsRootState {
    SUCCESS, LOADING, ERROR
}

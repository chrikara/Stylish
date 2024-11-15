package com.example.products.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.core.presentation.components.Screen
import com.example.core.presentation.components.ScreenState
import com.example.products.domain.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: ProductsRepository,
) : ViewModel() {

    private val id = savedStateHandle.toRoute<Screen.Edit>().id

    internal var state by mutableStateOf(ProductDetailsState())
        private set

    init {
        getProduct()
    }

    fun updateTitle(newTitle: String) {
        state = state.copy(title = newTitle)
    }

    fun updatePrice(newPrice: String) {
        state = state.copy(price = newPrice)
    }

    fun updateCategory(newCategory: String) {
        state = state.copy(category = newCategory)
    }

    fun updateDescription(newDescription: String) {
        state = state.copy(description = newDescription)
    }

    fun saveClicked(
        onSaveSuccessful: () -> Unit,
        onSaveFailed: () -> Unit
    ) {
        viewModelScope.launch {
            state = state.copy(isRunning = true)
            try {
                val result = repository.updateProduct(
                    id = id,
                    title = state.title,
                    description = state.description,
                    category = state.category,
                    price = state.price.toDoubleOrNull() ?: 0.0
                )

                result
                    .onSuccess {
                        onSaveSuccessful()
                    }
                    .onFailure {
                        onSaveFailed()
                        state = state.copy(isRunning = false)

                    }
            } catch (e: Exception) {
                onSaveFailed()
                state = state.copy(isRunning = false)
            }
        }
    }

    fun getProduct() {
        viewModelScope.launch {
            state = state.copy(screenState = ScreenState.LOADING)
            val result = repository.getProduct(id = id)

            state = state.copy(product = result.getOrNull())
            state = state.copy(
                screenState =
                if (result.isSuccess && state.product != null)
                    ScreenState.SUCCESS
                else
                    ScreenState.ERROR
            )
        }
    }
}

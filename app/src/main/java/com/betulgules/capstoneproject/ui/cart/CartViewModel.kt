package com.betulgules.capstoneproject.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.betulgules.capstoneproject.common.Resource
import com.betulgules.capstoneproject.data.model.response.ProductUI
import com.betulgules.capstoneproject.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel  @Inject constructor(private val productRepository: ProductRepository): ViewModel() {

    private var _cartState = MutableLiveData<CartState>()
    val cartState: LiveData<CartState> get() = _cartState


    fun getCartProducts(id: String) = viewModelScope.launch {
        _cartState.value = CartState.Loading

        _cartState.value = when (val result = productRepository.getCartProducts(id)) {
            is Resource.Success -> CartState.SuccessState(result.data)
            is Resource.Fail -> CartState.EmptyScreen(result.failMessage)
            is Resource.Error -> CartState.ShowPopUp(result.errorMessage)
        }
    }

    fun deleteFromCart(userId: String, id: Int) = viewModelScope.launch {
        _cartState.value = CartState.Loading

        _cartState.value = when (val result = productRepository.deleteFromCart(userId, id)) {
            is Resource.Success -> CartState.ShowPopUp(result.data.message.orEmpty())
            is Resource.Fail -> CartState.EmptyScreen(result.failMessage)
            is Resource.Error -> CartState.ShowPopUp(result.errorMessage)
        }
    }

    fun clearCart(userId: String) = viewModelScope.launch {
        _cartState.value = CartState.Loading

        _cartState.value = when (val result = productRepository.clearCart(userId)) {
            is Resource.Success -> CartState.ShowPopUp(result.data.message.orEmpty())
            is Resource.Fail -> CartState.EmptyScreen(result.failMessage)
            is Resource.Error -> CartState.ShowPopUp(result.errorMessage)
        }
    }
}

sealed interface CartState {
    object Loading: CartState
    data class SuccessState(val products: List<ProductUI>): CartState
    data class EmptyScreen(val failMessage: String): CartState
    data class ShowPopUp(val errorMessage: String): CartState
}
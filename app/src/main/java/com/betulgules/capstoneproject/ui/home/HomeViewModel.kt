package com.betulgules.capstoneproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.betulgules.capstoneproject.common.Resource
import com.betulgules.capstoneproject.data.model.response.ProductUI
import com.betulgules.capstoneproject.data.repository.AuthRepository
import com.betulgules.capstoneproject.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val productRepository: ProductRepository,
                                        private val authRepository: AuthRepository
): ViewModel(){

    private var _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState> get() = _homeState


    fun getProducts() = viewModelScope.launch{
        _homeState.value = HomeState.Loading
        _homeState.value = when (val result = productRepository.getProducts()) {
            is Resource.Success -> { HomeState.SuccessProductsState(result.data) }
            is Resource.Fail -> { HomeState.EmptyScreen(result.failMessage) }
            is Resource.Error -> { HomeState.ShowPopup(result.errorMessage) }
        }
    }

    fun getSaleProducts() = viewModelScope.launch{
        _homeState.value = HomeState.Loading
        _homeState.value = when (val result = productRepository.getSaleProducts()) {
            is Resource.Success -> { HomeState.SuccessSaleProductsState(result.data) }
            is Resource.Fail -> { HomeState.EmptyScreen(result.failMessage) }
            is Resource.Error -> { HomeState.ShowPopup(result.errorMessage) }

        }
    }

    fun logout() {
        authRepository.logOut()
        _homeState.value = HomeState.GoToSignIn
    }

    fun setFavoriteState(product: ProductUI) = viewModelScope.launch {
        if (product.isFav) {
            productRepository.deleteFromFavorites(product)
        } else {
            productRepository.addToFavorites(product)
        }
        getProducts()
        getSaleProducts()
    }

}

sealed interface HomeState {
    object Loading: HomeState
    object GoToSignIn: HomeState
    data class SuccessProductsState(val products: List<ProductUI>): HomeState
    data class SuccessSaleProductsState(val products: List<ProductUI>): HomeState

    data class EmptyScreen(val failMessage: String): HomeState
    data class ShowPopup(val errorMessage: String): HomeState
}

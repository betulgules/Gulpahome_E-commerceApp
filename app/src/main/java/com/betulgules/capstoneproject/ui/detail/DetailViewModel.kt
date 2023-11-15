package com.betulgules.capstoneproject.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.betulgules.capstoneproject.common.Resource
import com.betulgules.capstoneproject.data.model.request.AddToCartRequest
import com.betulgules.capstoneproject.data.model.response.BaseResponse
import com.betulgules.capstoneproject.data.model.response.ProductUI
import com.betulgules.capstoneproject.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(private val productRepository: ProductRepository): ViewModel() {

    private var _detailState = MutableLiveData<DetailState>()
    val detailState: LiveData<DetailState> get() = _detailState

    private var favproduct: ProductUI? = null

    fun getProductDetail(id: Int) = viewModelScope.launch {
        _detailState.value = DetailState.Loading

        _detailState.value = when (val result = productRepository.getProductDetail(id)) {
            is Resource.Success -> { favproduct = result.data
                DetailState.SuccessState(result.data)
            }
            is Resource.Fail -> DetailState.EmptyScreen(result.failMessage)
            is Resource.Error -> DetailState.ShowPopUp(result.errorMessage)

        }

    }

    fun addToCart(addToCartRequest: AddToCartRequest) = viewModelScope.launch {
        _detailState.value = DetailState.Loading

        _detailState.value = when (val result = productRepository.addToCart(addToCartRequest)) {
            is Resource.Success -> DetailState.SuccessAddToCartState(result.data)
            is Resource.Fail -> DetailState.EmptyScreen(result.failMessage)
            is Resource.Error -> DetailState.ShowPopUp(result.errorMessage)

        }

    }

    fun setFavoriteState() = viewModelScope.launch {
        favproduct?.let { favproduct ->
            if (favproduct.isFav) {
                productRepository.deleteFromFavorites(favproduct)
            } else {
                productRepository.addToFavorites(favproduct)
            }
            getProductDetail(favproduct.id)
        }
    }

}

sealed interface DetailState {
    object Loading: DetailState
    data class SuccessState(val product: ProductUI): DetailState
    data class SuccessAddToCartState(val addToCartResponse: BaseResponse): DetailState
    data class EmptyScreen(val failMessage: String): DetailState
    data class ShowPopUp(val errorMessage: String): DetailState
}
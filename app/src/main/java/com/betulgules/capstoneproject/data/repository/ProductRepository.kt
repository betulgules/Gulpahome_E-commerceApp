package com.betulgules.capstoneproject.data.repository

import com.betulgules.capstoneproject.common.Resource
import com.betulgules.capstoneproject.data.mapper.mapProductEntityToProductUI
import com.betulgules.capstoneproject.data.mapper.mapProductToProductUI
import com.betulgules.capstoneproject.data.mapper.mapToProductEntity
import com.betulgules.capstoneproject.data.mapper.mapToProductUI
import com.betulgules.capstoneproject.data.model.request.AddToCartRequest
import com.betulgules.capstoneproject.data.model.request.DeleteFromCartRequest
import com.betulgules.capstoneproject.data.model.response.BaseResponse
import com.betulgules.capstoneproject.data.model.response.GetSaleProductsResponse
import com.betulgules.capstoneproject.data.model.response.Product
import com.betulgules.capstoneproject.data.model.response.ProductUI
import com.betulgules.capstoneproject.data.source.local.ProductDao
import com.betulgules.capstoneproject.data.source.remote.ProductService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository(private val productService: ProductService, private val productDao: ProductDao) {

    suspend fun getProducts(): Resource<List<ProductUI>> = withContext(Dispatchers.IO){
        try {
            val favorites = productDao.getProductIds()
            val response = productService.getProducts().body()

            if (response?.status == 200) {
                Resource.Success(response.products.orEmpty().mapProductToProductUI(favorites))
            } else {
                Resource.Fail(response?.message.orEmpty())
            }

        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun getSaleProducts(): Resource<List<ProductUI>> = withContext(Dispatchers.IO){
        try {
            val favorites = productDao.getProductIds()
            val response = productService.getSaleProducts().body()

            if (response?.status == 200) {
                Resource.Success(response.products.orEmpty().mapProductToProductUI(favorites))
            } else {
                Resource.Fail(response?.message.orEmpty())
            }

        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }
    suspend fun getProductDetail(id: Int): Resource<ProductUI> = withContext(Dispatchers.IO){
        try {
            val favorites = productDao.getProductIds()
            val response = productService.getProductDetail(id).body()
            if (response?.status == 200 && response.product != null) {
                Resource.Success(response.product.mapToProductUI(favorites))
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun addToCart(addToCartRequest: AddToCartRequest): Resource<BaseResponse> = withContext(
        Dispatchers.IO){
        try {
            val response = productService.addToCart(addToCartRequest).body()
            if (response?.status == 200) {
                Resource.Success(response)
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun getCartProducts(userId: String): Resource<List<ProductUI>> = withContext(Dispatchers.IO){
        try {
            val favorites = productDao.getProductIds()
            val response = productService.getCartProducts(userId).body()
            if (response?.status == 200 && response.message != null) {
                Resource.Success(response.products.orEmpty().mapProductToProductUI(favorites))
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun deleteFromCart(userId: String, id: Int): Resource<BaseResponse> = withContext(
        Dispatchers.IO){
        try {
            val response = productService.deleteFromCart(DeleteFromCartRequest(id, userId)).body()
            if (response?.status == 200 && response.message != null) {
                Resource.Success(response)
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun clearCart(id: String): Resource<BaseResponse> = withContext(Dispatchers.IO){
        try {
            val response = productService.clearCart(id).body()
            if (response?.status == 200 && response.message != null) {
                Resource.Success(response)
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun getSearchResult(query: String): Resource<List<ProductUI>> =
        withContext(Dispatchers.IO) {
            try {
                val favorites = productDao.getProductIds()
                val response = productService.getSearchResult(query).body()

                if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty().mapProductToProductUI(favorites))
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun addToFavorites(productUI: ProductUI) {
        productDao.addProduct(productUI.mapToProductEntity())
    }

    suspend fun deleteFromFavorites(productUI: ProductUI) {
        productDao.deleteProduct(productUI.mapToProductEntity())
    }

    suspend fun clearAllFromFavorites() {
        productDao.clearAllFromFavorites()
    }


    suspend fun getFavorites(): Resource<List<ProductUI>> =
        withContext(Dispatchers.IO) {
            try {
                val products = productDao.getProducts()

                if (products.isEmpty()) {
                    Resource.Fail("Products not found")
                } else {
                    Resource.Success(products.mapProductEntityToProductUI())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }




}
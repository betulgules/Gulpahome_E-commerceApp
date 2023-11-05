package com.betulgules.capstoneproject.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.betulgules.capstoneproject.data.model.response.GetProductsResponse
import com.betulgules.capstoneproject.data.model.response.GetSaleProductsResponse
import com.betulgules.capstoneproject.data.model.response.Product
import com.betulgules.capstoneproject.data.source.remote.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository(private val productService: ProductService) {

    var productsLiveData = MutableLiveData<List<Product>>()

    fun getProducts() {
        productService.getProducts()
            .enqueue(object : Callback<GetProductsResponse> {

                override fun onResponse(
                    call: Call<GetProductsResponse>,
                    response: Response<GetProductsResponse>
                ) {
                    val result = response.body()

                    if (result?.status == 200) {
                        productsLiveData.value = result.products.orEmpty()

                    } else {
                        //400
                    }
                }

                override fun onFailure(call: Call<GetProductsResponse>, t: Throwable) {
                    Log.e("GetProducts", t.message.orEmpty())
                }
            })
    }
}
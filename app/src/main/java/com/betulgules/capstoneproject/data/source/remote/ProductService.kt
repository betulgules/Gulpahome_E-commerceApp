package com.betulgules.capstoneproject.data.source.remote

import com.betulgules.capstoneproject.data.model.request.AddToCartRequest
import com.betulgules.capstoneproject.data.model.request.DeleteFromCartRequest
import com.betulgules.capstoneproject.data.model.response.BaseResponse
import com.betulgules.capstoneproject.data.model.response.GetCartProductsResponse
import com.betulgules.capstoneproject.data.model.response.GetProductDetailResponse
import com.betulgules.capstoneproject.data.model.response.GetProductsResponse
import com.betulgules.capstoneproject.data.model.response.GetSaleProductsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductService {

    @GET("get_products.php")
    suspend fun getProducts(): Response<GetProductsResponse>

    @GET("get_sale_products.php")
    suspend fun getSaleProducts(): Response<GetSaleProductsResponse>

    @GET("get_product_detail.php")
    suspend fun getProductDetail(
        @Query("id") id: Int
    ): Response<GetProductDetailResponse>

    @POST("add_to_cart.php")
    suspend fun addToCart(
        @Body addToCartRequest: AddToCartRequest
    ): Response<BaseResponse>

    @POST("delete_from_cart.php")
    suspend fun deleteFromCart(
        @Body deleteFromCartRequest: DeleteFromCartRequest
    ): Response<BaseResponse>

    @GET("get_cart_products.php")
    suspend fun getCartProducts(
        @Query("userId") userId: String?): Response<GetCartProductsResponse>

    @POST("clear_cart.php")
    suspend fun clearCart(
        @Body user: String
    ): Response<BaseResponse>


    @GET("search_product.php")
    suspend fun getSearchResult(
        @Query("query") query: String
    ): Response<GetProductsResponse>

}
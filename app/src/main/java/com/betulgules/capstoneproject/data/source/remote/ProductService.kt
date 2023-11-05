package com.betulgules.capstoneproject.data.source.remote

import com.betulgules.capstoneproject.data.model.request.AddToCartRequest
import com.betulgules.capstoneproject.data.model.request.DeleteFromCartRequest
import com.betulgules.capstoneproject.data.model.response.AddToCartResponse
import com.betulgules.capstoneproject.data.model.response.ClearCartResponse
import com.betulgules.capstoneproject.data.model.response.DeleteFromCartResponse
import com.betulgules.capstoneproject.data.model.response.GetCartProductsResponse
import com.betulgules.capstoneproject.data.model.response.GetCategoriesResponse
import com.betulgules.capstoneproject.data.model.response.GetProductDetailResponse
import com.betulgules.capstoneproject.data.model.response.GetProductsByCategoryResponse
import com.betulgules.capstoneproject.data.model.response.GetProductsResponse
import com.betulgules.capstoneproject.data.model.response.GetSaleProductsResponse
import com.betulgules.capstoneproject.data.model.response.SearchProductResponse
import com.betulgules.capstoneproject.data.model.response.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductService {

    @GET("get_products.php")
    fun getProducts(): Call<GetProductsResponse>

    @GET("get_product_detail.php")
    fun getProductDetail(
        @Query("id") id: Int
    ): Call<GetProductDetailResponse>

    @POST("add_to_cart.php")
    fun addToCart(
        @Body addToCartRequest: AddToCartRequest
    ): Call<AddToCartResponse>

    @POST("delete_from_cart.php")
    fun deleteFromCart(
        @Body deleteFromCartRequest: DeleteFromCartRequest
    ): Call<DeleteFromCartResponse>

    @GET("get_cart_products.php")
    fun getCartProducts(
        @Query("userId") userId: String?): Call<GetCartProductsResponse>

    @POST("clear_cart.php")
    fun clearCart(
        @Body user: User
    ): Call<ClearCartResponse>

    @GET("get_products_by_category.php")
    fun getProductsByCategory(
        @Query("category") category: String
    ): Call<GetProductsByCategoryResponse>

    @GET("get_sale_products.php")
    fun getSaleProducts(): Call<GetSaleProductsResponse>

    @GET("search_product.php")
    fun searchProduct(): Call<SearchProductResponse>

    @GET("get_categories.php")
    fun getCategories(): Call<GetCategoriesResponse>

}
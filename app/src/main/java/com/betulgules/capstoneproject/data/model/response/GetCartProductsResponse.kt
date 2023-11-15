package com.betulgules.capstoneproject.data.model.response

data class GetCartProductsResponse(
    val products: List<Product>?
): BaseResponse()

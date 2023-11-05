package com.betulgules.capstoneproject.data.model.response

data class GetProductsByCategoryResponse(
    val products: List<Product>?,
    val status: Int?,
    val message: String?
)

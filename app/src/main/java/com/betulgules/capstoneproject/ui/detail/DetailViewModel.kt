package com.betulgules.capstoneproject.ui.detail

import androidx.lifecycle.ViewModel
import com.betulgules.capstoneproject.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(private val productRepository: ProductRepository): ViewModel() {
}
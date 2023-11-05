package com.betulgules.capstoneproject.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.betulgules.capstoneproject.MainApplication
import com.betulgules.capstoneproject.R
import com.betulgules.capstoneproject.common.viewBinding
import com.betulgules.capstoneproject.data.model.response.GetProductsResponse
import com.betulgules.capstoneproject.data.model.response.GetSaleProductsResponse
import com.betulgules.capstoneproject.databinding.FragmentHomeBinding
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@HiltAndroidApp
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel by viewModels<HomeViewModel>()

    private val productsAdapter = ProductsAdapter(onProductClick = ::onProductClick)
    private val saleProductsAdapter = SaleProductsAdapter(onSaleProductClick = ::onSaleProductClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProducts()

        with(binding) {
            rvProducts.adapter = productsAdapter
            rvSaleProducts.adapter = saleProductsAdapter
        }

        observeData()
    }

    private fun observeData(){
        viewModel.productsLiveData.observe(viewLifecycleOwner) {

        }

    }


    private fun onProductClick(id: Int) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(id))
    }

    private fun onSaleProductClick(id: Int) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(id))
    }
}




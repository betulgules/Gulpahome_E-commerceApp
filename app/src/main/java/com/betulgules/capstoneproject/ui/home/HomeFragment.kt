package com.betulgules.capstoneproject.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.betulgules.capstoneproject.R
import com.betulgules.capstoneproject.common.gone
import com.betulgules.capstoneproject.common.viewBinding
import com.betulgules.capstoneproject.common.visible
import com.betulgules.capstoneproject.data.model.response.ProductUI
import com.betulgules.capstoneproject.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val viewModel by viewModels<HomeViewModel>()

    private val productsAdapter = ProductsAdapter(onProductClick = ::onProductClick, onFavClick = ::onFavClick)

    private val saleProductsAdapter = SaleProductsAdapter(onProductClick = ::onProductClick, onFavClick = ::onFavClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProducts()
        viewModel.getSaleProducts()

        with(binding) {
            rvProducts.adapter = productsAdapter
            rvSaleProducts.adapter = saleProductsAdapter

            ivBack.setOnClickListener {
                viewModel.logout()
            }
        }
        observeData()
    }

    private fun observeData() = with(binding){
        viewModel.homeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                HomeState.Loading -> {
                    progressBar.visible()
                }
                is HomeState.SuccessProductsState -> {
                    progressBar.gone()
                    productsAdapter.submitList(state.products)
                }
                is HomeState.SuccessSaleProductsState -> {
                    progressBar.gone()
                    saleProductsAdapter.submitList(state.products)
                }
                is HomeState.EmptyScreen -> {
                    progressBar.gone()
                    ivEmpty.visible()
                    tvEmpty.visible()
                    tvEmpty.text = state.failMessage


                }
                is HomeState.ShowPopup -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage,1000  ).show()
                }

                HomeState.GoToSignIn -> {
                    findNavController().navigate(R.id.homeToMain)
                }
            }


        }}

    private fun onProductClick(id: Int) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(id))
    }


    private fun onFavClick(product: ProductUI) {
        viewModel.setFavoriteState(product)
    }
}

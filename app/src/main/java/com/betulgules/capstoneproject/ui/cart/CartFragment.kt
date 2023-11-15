package com.betulgules.capstoneproject.ui.cart

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.betulgules.capstoneproject.R
import com.betulgules.capstoneproject.common.viewBinding
import com.betulgules.capstoneproject.common.visible
import com.betulgules.capstoneproject.databinding.FragmentCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {
    private val binding by viewBinding(FragmentCartBinding::bind)

    private val viewModel by viewModels<CartViewModel>()

    private val cartAdapter = CartAdapter(onProductClick = ::onProductClick, onDeleteClick = ::onDeleteClick)

    private lateinit var auth: FirebaseAuth
    private var userId: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = com.google.firebase.ktx.Firebase.auth

        userId = auth.currentUser?.uid

        viewModel.getCartProducts(userId.toString())

        with(binding) {
            rvCart.adapter = cartAdapter
            clearButton.setOnClickListener {
                viewModel.clearCart(userId.toString())
            }
        }

        binding.buttonOdeme.setOnClickListener {
            findNavController().navigate(R.id.cartToPayment)
        }

        observeData()
    }

    private fun observeData() {
        viewModel.cartState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CartState.Loading -> {
                    binding.progressBar.visible()
                }

                is CartState.SuccessState -> {
                    binding.progressBar.isGone = true
                    cartAdapter.submitList(state.products)
                }

                is CartState.ShowPopUp -> {
                    binding.progressBar.isGone = true
                    Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_SHORT).show()
                }

                is CartState.EmptyScreen -> {
                    binding.progressBar.isGone = true
                    binding.ivEmpty.visible()
                    binding.tvEmpty.visible()
                    binding.rvCart.isGone = true
                    binding.tvEmpty.text = state.failMessage

                }
            }
        }
    }


    private fun onProductClick(id: Int) {
        findNavController().navigate(CartFragmentDirections.actionCartFragmentToDetailFragment(id))
    }

    private fun onDeleteClick(id: Int) {
        viewModel.deleteFromCart(userId.orEmpty(), id)
    }
}
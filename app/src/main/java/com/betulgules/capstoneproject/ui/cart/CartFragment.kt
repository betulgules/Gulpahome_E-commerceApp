package com.betulgules.capstoneproject.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.betulgules.capstoneproject.MainApplication
import com.betulgules.capstoneproject.R
import com.betulgules.capstoneproject.common.viewBinding
import com.betulgules.capstoneproject.data.model.request.DeleteFromCartRequest
import com.betulgules.capstoneproject.data.model.response.ClearCartResponse
import com.betulgules.capstoneproject.data.model.response.DeleteFromCartResponse
import com.betulgules.capstoneproject.data.model.response.GetCartProductsResponse
import com.betulgules.capstoneproject.data.model.response.User
import com.betulgules.capstoneproject.databinding.FragmentCartBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CartFragment : Fragment(R.layout.fragment_cart) {
    private val binding by viewBinding(FragmentCartBinding::bind)

    private val cartAdapter = CartAdapter(onProductClick = ::onProductClick, onDeleteClick = ::onDeleteClick)

    private lateinit var auth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        auth.currentUser?.uid

        getCartProducts(id)

        with(binding) {
            rvCart.adapter = cartAdapter
            clearButton.setOnClickListener {
                clearCart(id)

            }
        }

        binding.buttonOdeme.setOnClickListener {
            findNavController().navigate(R.id.cartToPayment)
        }

    }



    private fun getCartProducts(id: Int) {
        val userId = auth.currentUser!!.uid
        MainApplication.productService?.getCartProducts(userId)
            ?.enqueue(object : Callback<GetCartProductsResponse> {

                override fun onResponse(
                    call: Call<GetCartProductsResponse>,
                    response: Response<GetCartProductsResponse>
                ) {
                    val result = response.body()

                    if (result?.status == 200) {
                        cartAdapter.submitList(result.products.orEmpty())
                    } else {
                        Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetCartProductsResponse>, t: Throwable) {
                    Log.e("GetCartProducts", t.message.orEmpty())
                }
            })
    }


    private fun onDeleteClick(id: Int) {
        val deleteItem = DeleteFromCartRequest(
            id = id
        )

        MainApplication.productService?.deleteFromCart(deleteItem)
            ?.enqueue(object : Callback<DeleteFromCartResponse> {

                override fun onResponse(
                    call: Call<DeleteFromCartResponse>,
                    response: Response<DeleteFromCartResponse>
                ) {
                    val result = response.body()

                    if (result?.status == 200) {
                        Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                       getCartProducts(id)

                    } else {
                        Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DeleteFromCartResponse>, t: Throwable) {
                    Log.e("GetCartProducts", t.message.orEmpty())
                }
            })
    }

    private fun clearCart(id: Int) {

        val user = User(
            userId = auth.currentUser!!.uid
        )

        MainApplication.productService?.clearCart(user)
            ?.enqueue(object : Callback<ClearCartResponse> {
                override fun onResponse(
                    call: Call<ClearCartResponse>,
                    response: Response<ClearCartResponse>
                ) {
                    val result = response.body()

                    if (result?.status == 200) {
                        Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                        binding.rvCart.isGone = true

                        getCartProducts(id)

                    } else {
                        Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ClearCartResponse>, t: Throwable) {
                    Log.e("ClearCart", t.message.orEmpty())
                }
            })
    }



    private fun onProductClick(id: Int) {
        findNavController().navigate(CartFragmentDirections.actionCartFragmentToDetailFragment(id))
    }
}
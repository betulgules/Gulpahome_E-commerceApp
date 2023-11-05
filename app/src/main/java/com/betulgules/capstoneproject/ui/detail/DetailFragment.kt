package com.betulgules.capstoneproject.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.betulgules.capstoneproject.MainApplication
import com.betulgules.capstoneproject.R
import com.betulgules.capstoneproject.common.viewBinding
import com.betulgules.capstoneproject.data.model.request.AddToCartRequest
import com.betulgules.capstoneproject.data.model.response.AddToCartResponse
import com.betulgules.capstoneproject.data.model.response.GetProductDetailResponse
import com.betulgules.capstoneproject.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@HiltAndroidApp
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)

    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getProductDetail(args.id)
        val id = args.id
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        binding.buttonsepetekle.setOnClickListener {
            addToCart(AddToCartRequest(userId, id))
        }

    }

    private fun getProductDetail(id: Int) {
        MainApplication.productService?.getProductDetail(id)?.enqueue(object :
            Callback<GetProductDetailResponse> {
            override fun onResponse(
                call: Call<GetProductDetailResponse>,
                response: Response<GetProductDetailResponse>
            ) {
                val result = response.body()

                if (result?.status == 200 && result.product != null) {
                    with(binding) {
                        result.product.let {
                            Glide.with(ivProduct).load(it.imageOne).into(ivProduct)
                            tvTitle.text = it.title
                            tvPrice.text = "${it.price} ₺"
                            tvDescription.text = it.description
                        }
                        ivBack.setOnClickListener{
                            findNavController().navigateUp()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetProductDetailResponse>, t: Throwable) {
                Log.e("GetProductDetail", t.message.orEmpty())
            }
        })
    }

    private fun addToCart(addToCartRequest: AddToCartRequest){
        MainApplication.productService?.addToCart(addToCartRequest)?.enqueue(object :
            Callback<AddToCartResponse> {
            override fun onResponse(
                call: Call<AddToCartResponse>,
                response: Response<AddToCartResponse>
            ) {
                val result = response.body()

                if (result?.status == 200) {
                    findNavController().popBackStack()
                    Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {
                Log.e("AddToCart", t.message.orEmpty())
            }

        })
    }
}
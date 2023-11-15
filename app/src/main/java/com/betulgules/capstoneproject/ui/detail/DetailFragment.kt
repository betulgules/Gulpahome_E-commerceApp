package com.betulgules.capstoneproject.ui.detail

import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.betulgules.capstoneproject.R
import com.betulgules.capstoneproject.common.gone
import com.betulgules.capstoneproject.common.viewBinding
import com.betulgules.capstoneproject.common.visible
import com.betulgules.capstoneproject.data.model.request.AddToCartRequest
import com.betulgules.capstoneproject.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding by viewBinding(FragmentDetailBinding::bind)

    private val viewModel by viewModels<DetailViewModel>()

    private val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel.getProductDetail(args.id)
        val id = args.id
        val userId = FirebaseAuth.getInstance().currentUser!!.uid



        binding.btnAddToCart.setOnClickListener {
            viewModel.addToCart(AddToCartRequest(userId, id))
        }
        binding.ivFav.setOnClickListener {
             viewModel.setFavoriteState()
         }
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        observeData()

    }

    private fun observeData() = with(binding) {
        viewModel.detailState.observe(viewLifecycleOwner) { state ->
            when (state) {
                DetailState.Loading -> progressBar.visible()

                is DetailState.SuccessState -> {
                    progressBar.gone()
                    Glide.with(ivProduct).load(state.product.imageOne).into(ivProduct)
                    tvTitle.text = state.product.title
                    tvPrice.text = "${state.product.price} ₺"

                    ivFav.setBackgroundResource(
                        if (state.product.isFav) R.drawable.ic_fav_selected
                        else R.drawable.ic_fav_unselected
                    )

                    if (state.product.saleState) {
                        tvSalePrice.visibility = View.VISIBLE
                        tvSalePrice.text = "${state.product.salePrice} ₺"
                        tvPrice.paintFlags = tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    } else {
                        tvSalePrice.visibility = View.GONE
                        tvPrice.paintFlags = 0
                    }
                    tvDescription.text = state.product.description

                }

                is DetailState.SuccessAddToCartState -> {
                    findNavController().popBackStack()
                }

                is DetailState.EmptyScreen -> {
                    progressBar.gone()
                    ivEmpty.visible()
                    tvEmpty.visible()
                    tvEmpty.text = state.failMessage
                }

                is DetailState.ShowPopUp -> {
                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }

}
package com.betulgules.capstoneproject.ui.cart

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.betulgules.capstoneproject.data.model.response.ProductUI
import com.betulgules.capstoneproject.databinding.ItemCartBinding
import com.bumptech.glide.Glide

class CartAdapter(
    private val onProductClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : ListAdapter<ProductUI, CartAdapter.CartViewHolder>(CartViewHolder.ProductDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onProductClick,
            onDeleteClick)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) = holder.bind(getItem(position))

    class CartViewHolder(
        private val binding: ItemCartBinding,
        private val onProductClick: (Int) -> Unit,
        private val onDeleteClick: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductUI) {
            with(binding) {
                tvTitleCart.text = product.title
                tvPrice.text = "${product.price} ₺"

                if (product.saleState == true) {
                    tvSalePrice.visibility = View.VISIBLE
                    tvSalePrice.text = "${product.salePrice} ₺"
                    tvSalePrice.paintFlags = tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    tvSalePrice.visibility = View.GONE
                    tvPrice.paintFlags = 0
                }

                Glide.with(ivCart).load(product.imageOne).into(ivCart)

                ivDelete.setOnClickListener {
                    onDeleteClick(product.id)
                }

                root.setOnClickListener {
                    onProductClick(product.id)
                }


            }
        }

        class ProductDiffUtilCallBack : DiffUtil.ItemCallback<ProductUI>() {
            override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
                return oldItem == newItem
            }
        }
    }
}

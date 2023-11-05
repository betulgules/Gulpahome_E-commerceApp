package com.betulgules.capstoneproject.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.betulgules.capstoneproject.data.model.response.Product
import com.betulgules.capstoneproject.databinding.ItemCartBinding
import com.bumptech.glide.Glide
import kotlin.reflect.KFunction1

class CartAdapter(
    private val onProductClick: (Int) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : ListAdapter<Product, CartAdapter.CartViewHolder>(CartViewHolder.ProductDiffUtilCallBack()) {

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

        fun bind(product: Product) {
            with(binding) {
                tvTitleCart.text = product.title
                tvPriceCart.text = "${product.price} ₺"

                Glide.with(ivCart).load(product.imageOne).into(ivCart)

                ivDeleteCart.setOnClickListener {
                    onDeleteClick(product.id ?: 1)
                }

                root.setOnClickListener {
                    onProductClick(product.id ?: 1)
                }


            }
    }

        class ProductDiffUtilCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
    }
}

package com.betulgules.capstoneproject.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.betulgules.capstoneproject.data.model.response.Product
import com.betulgules.capstoneproject.databinding.ItemSaleBinding
import com.bumptech.glide.Glide

class SaleProductsAdapter(
    private val onSaleProductClick: (Int) -> Unit
) : ListAdapter<Product, SaleProductsAdapter.SaleProductsViewHolder>(ProductDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleProductsViewHolder {
        return SaleProductsViewHolder(
            ItemSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onSaleProductClick
        )
    }

    override fun onBindViewHolder(holder: SaleProductsViewHolder, position: Int) = holder.bind(getItem(position))

    class SaleProductsViewHolder(
        private val binding: ItemSaleBinding,
        private val onProductClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
                tvTitleSale.text = product.title
                tvPriceSale.text = "${product.saleState} ₺"

                Glide.with(ivSale).load(product.imageOne).into(ivSale)

                root.setOnClickListener {
                    onProductClick(product.id ?: 1)
                }
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
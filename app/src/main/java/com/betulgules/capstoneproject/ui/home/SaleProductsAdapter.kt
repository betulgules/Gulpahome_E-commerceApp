package com.betulgules.capstoneproject.ui.home

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.betulgules.capstoneproject.R
import com.betulgules.capstoneproject.data.model.response.ProductUI
import com.betulgules.capstoneproject.databinding.ItemSaleBinding
import com.bumptech.glide.Glide

class SaleProductsAdapter(
    private val onProductClick: (Int) -> Unit,
    private val onFavClick: (ProductUI) -> Unit

) : ListAdapter<ProductUI, SaleProductsAdapter.SaleProductsViewHolder>(ProductDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleProductsViewHolder {
        return SaleProductsViewHolder(
            ItemSaleBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onProductClick, onFavClick
        )
    }

    override fun onBindViewHolder(holder: SaleProductsViewHolder, position: Int) =
        holder.bind(getItem(position))

    class SaleProductsViewHolder(
        private val binding: ItemSaleBinding,
        private val onProductClick: (Int) -> Unit,
        private val onFavClick: (ProductUI) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductUI) {
            with(binding) {
                tvTitleSale.text = product.title
                tvPrice.text = "${product.price} ₺"

                if (product.saleState == true) {
                    tvSalePrice.visibility = View.VISIBLE
                    tvSalePrice.text = "${product.salePrice} ₺"
                    tvSalePrice.paintFlags = tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    tvSalePrice.visibility = View.GONE
                    tvPrice.paintFlags = 0
                }
                ivFavorite.setBackgroundResource(
                    if (product.isFav) R.drawable.ic_fav_selected
                    else R.drawable.ic_fav_unselected
                )
                ivFavorite.setOnClickListener {
                    onFavClick(product)
                }

                Glide.with(ivSale).load(product.imageOne).into(ivSale)

                root.setOnClickListener {
                    onProductClick(product.id ?: 1)
                }
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
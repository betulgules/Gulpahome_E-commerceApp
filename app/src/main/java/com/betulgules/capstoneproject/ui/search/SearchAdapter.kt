package com.betulgules.capstoneproject.ui.search

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.betulgules.capstoneproject.data.model.response.ProductUI
import com.betulgules.capstoneproject.databinding.ItemSearchBinding
import com.bumptech.glide.Glide

class SearchAdapter (
    private val onProductClick: (Int) -> Unit,
) : ListAdapter<ProductUI, SearchAdapter.SearchViewHolder>(SearchViewHolder.ProductDiffUtilCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):SearchViewHolder {
        return SearchViewHolder(
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onProductClick,)
    }


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) = holder.bind(getItem(position))

    class SearchViewHolder(
        private val binding: ItemSearchBinding,
        private val onProductClick: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductUI) {
            with(binding) {
                tvTitleSearch.text = product.title
                tvPrice.text = "${product.price} ₺"

                if (product.saleState == true) {
                    tvSalePrice.visibility = View.VISIBLE
                    tvSalePrice.text = "${product.salePrice} ₺"
                    tvSalePrice.paintFlags = tvPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    tvSalePrice.visibility = View.GONE
                    tvPrice.paintFlags = 0
                }

                Glide.with(ivSearch).load(product.imageOne).into(ivSearch)

                root.setOnClickListener {
                    onProductClick(product.id ?: 1) }
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
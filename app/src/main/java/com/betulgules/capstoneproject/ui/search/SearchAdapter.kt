package com.betulgules.capstoneproject.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.betulgules.capstoneproject.data.model.response.Product
import com.betulgules.capstoneproject.databinding.ItemSearchBinding
import com.bumptech.glide.Glide

class SearchAdapter (
    private val onProductClick: (Int) -> Unit,
    ) : ListAdapter<Product, SearchAdapter.SearchViewHolder>(SearchViewHolder.ProductDiffUtilCallBack()) {


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

            fun bind(product: Product) {
                with(binding) {
                    tvTitleSearch.text = product.title
                    tvPriceSearch.text = "${product.price} ₺"

                    Glide.with(ivSearch).load(product.imageOne).into(ivSearch)

                    root.setOnClickListener {
                        onProductClick(product.id ?: 1) }
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
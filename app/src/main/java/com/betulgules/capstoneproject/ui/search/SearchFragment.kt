package com.betulgules.capstoneproject.ui.search

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.betulgules.capstoneproject.R
import com.betulgules.capstoneproject.common.viewBinding
import com.betulgules.capstoneproject.databinding.FragmentSearchBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)

    private val viewModel by viewModels<SearchViewModel>()

    private val searchAdapter = SearchAdapter(
        onProductClick = ::onProductClick
    )
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.adapter = searchAdapter

            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }

            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    newText?.let {

                        if (it.length >= 3) {
                            viewModel.searchProduct(it)

                        }
                        else {
                            searchAdapter.submitList(emptyList())
                        }
                    }
                    return true
                }
            })
        }
        observeData()
    }

    private fun observeData() {
        viewModel.searchState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SearchState.Loading -> {

                }
                is SearchState.SuccessState -> {

                    searchAdapter.submitList(state.products)
                }
                is SearchState.ShowPopUp -> {

                    Snackbar.make(requireView(), state.errorMessage, 1000).show()

                }
                is SearchState.EmptyScreen -> {

                    Snackbar.make(requireView(), state.failMessage, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onProductClick(id: Int) {
        findNavController().navigate(SearchFragmentDirections.searchToDetail(id))
    }

}

package com.betulgules.capstoneproject.ui.search
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import com.betulgules.capstoneproject.MainApplication
import com.betulgules.capstoneproject.R
import com.betulgules.capstoneproject.common.viewBinding
import com.betulgules.capstoneproject.data.model.response.SearchProductResponse
import com.betulgules.capstoneproject.databinding.FragmentSearchBinding
import com.betulgules.capstoneproject.ui.home.ProductsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment(R.layout.fragment_search){

    //private val binding by viewBinding(FragmentSearchBinding::bind)

    //private val productsAdapter: ProductsAdapter = ProductsAdapter(onProductClick = ::onProductClick)

    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        searchView = view.findViewById(R.id.searchView)
        recyclerView = view.findViewById(R.id.recyclerView)


        // RecyclerView için bir LinearLayoutManager ayarlayın
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // SearchView'a metin girildiğinde dinleyici ekleyin
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Kullanıcı arama yapmak için "Enter" tuşuna bastığında burası çalışır
                query?.let {}
                SearchProduct()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Arama metni her değiştiğinde burası çalışır
                // (isteğe bağlı olarak burada canlı arama yapabilirsiniz)
                return false
            }
        })

        return view
    }

    private fun SearchProduct() {
        MainApplication.productService?.searchProduct()
            ?.enqueue(object : Callback<SearchProductResponse> {

                override fun onResponse(
                    call: Call<SearchProductResponse>,
                    response: Response<SearchProductResponse>
                ) {
                    val result = response.body()
        // Burada search_product.php veya başka bir yöntemle arama yapabilir ve sonuçları alabilirsiniz.
        // Aldığınız sonuçları RecyclerView ile göstermelisiniz.
        // Örnek olarak, verileri bir liste ile doldurup RecyclerView'a bağlamayı unutmayın.
    }

                override fun onFailure(call: Call<SearchProductResponse>, t: Throwable) {
                    Log.e("SearchProduct", t.message.orEmpty())
                }
            })
    }

    private fun onProductClick(productList: ArrayList<SearchProductResponse>) {
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailFragment(id))
    }
}
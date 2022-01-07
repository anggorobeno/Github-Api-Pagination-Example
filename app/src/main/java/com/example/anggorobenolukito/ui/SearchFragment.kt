package com.example.anggorobenolukito.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anggorobenolukito.R
import com.example.anggorobenolukito.adapter.UserAdapter
import com.example.anggorobenolukito.databinding.FragmentSearchBinding
import com.example.anggorobenolukito.utils.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding : FragmentSearchBinding
    private val TAG = "SearchFragment"
    private val viewModel : SearchViewModel by viewModels()
    private val userAdapter : UserAdapter = UserAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userSearch()
    }

    private fun userSearch() {
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val svUsers = binding.svUsers
        svUsers.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        svUsers.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                svUsers.clearFocus()
                if (query != null) {
                    if (query.isNotEmpty()){
                        search(query)
                    }
                    else Snackbar.make(requireView(),"Please Enter Username",Snackbar.LENGTH_SHORT).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun search(query: String) {
        viewModel.searchUser(query)
        viewModel.usersss.observe(viewLifecycleOwner,{
            when(it){
                is Resource.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                is Resource.Success ->{
                    binding.progressCircular.visibility = View.GONE
                    binding.ivEmptyStateArticle.visibility = View.GONE
                    binding.tvEmptyStateArticleDesc.visibility = View.GONE
                    val result = it.data
                    Log.d(TAG, "search: $result ")
                    userAdapter.setListUser(result)
                    showRvArticle()
                }
                is Resource.Error -> {
                    binding.progressCircular.visibility = View.GONE
                    binding.ivEmptyStateArticle.visibility = View.VISIBLE
                    binding.tvEmptyStateArticleDesc.visibility = View.VISIBLE
                    binding.tvEmptyStateArticleDesc.text = it.message
                    Snackbar.make(requireView(),"Error Occurred :  ${it.message}",Snackbar.LENGTH_SHORT).show()
                }

            }
        })

    }
    private fun showRvArticle() {
        with(binding.rvUser) {
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            this.adapter = userAdapter
        }
    }

    override fun onDestroyView() {
        Log.d(TAG, "onDestroyView: Tes")
        super.onDestroyView()
    }

}
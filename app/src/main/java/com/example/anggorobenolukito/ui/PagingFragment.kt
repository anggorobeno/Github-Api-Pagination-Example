package com.example.anggorobenolukito.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anggorobenolukito.R
import com.example.anggorobenolukito.adapter.UserAdapter
import com.example.anggorobenolukito.adapter.UserLoadStateAdapter
import com.example.anggorobenolukito.adapter.UserPagingAdapter
import com.example.anggorobenolukito.databinding.FragmentPagingBinding
import com.example.anggorobenolukito.databinding.FragmentSearchBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagingFragment : Fragment() {
    private var _binding: FragmentPagingBinding? = null
    private val binding get() = _binding!!
    private val TAG = "Paging Fragment"
    private val viewModel: PagingViewModel by viewModels()
    private val userAdapter: UserPagingAdapter = UserPagingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPagingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.observe(viewLifecycleOwner, {
            userAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            binding.progressCircular.visibility = View.GONE
            showRvArticle()
        })
        userSearch()
        userAdapter.addLoadStateListener { combinedLoadStates ->
            with(binding) {
                // Default State
                progressCircular.isVisible = combinedLoadStates.source.refresh is LoadState.Loading
                rvUser.isVisible = combinedLoadStates.source.refresh is LoadState.NotLoading
                tvEmptyStateArticleDesc.isVisible =
                    combinedLoadStates.source.refresh is LoadState.Error
                tvEmptyStateArticleDesc.isVisible =
                    combinedLoadStates.source.refresh is LoadState.Error
                ivEmptyStateArticle.isVisible = combinedLoadStates.source.refresh is LoadState.Error
                btnRetry.isVisible = combinedLoadStates.source.refresh is LoadState.Error

                // No Internet Connection State
                if (combinedLoadStates.source.refresh is LoadState.Error) {
                    rvUser.isVisible = false
                    tvEmptyStateArticleDesc.text = getString(R.string.something_went_wrong)
                    tvEmptyStateArticleDesc.isVisible = true
                    btnRetry.isVisible = true
                }
                // Empty State Result
                if (combinedLoadStates.source.refresh is LoadState.NotLoading
                    && combinedLoadStates.append.endOfPaginationReached && userAdapter.itemCount < 1
                ) {
                    rvUser.isVisible = false
                    ivEmptyStateArticle.isVisible = true
                    tvEmptyStateArticleDesc.text = getString(R.string.no_user_found)
                    tvEmptyStateArticleDesc.isVisible = true
                }
            }


        }
    }

    private fun showRvArticle() {
        with(binding.rvUser) {
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            this.adapter = userAdapter.withLoadStateHeaderAndFooter(
                header = UserLoadStateAdapter { userAdapter.retry() },
                footer = UserLoadStateAdapter { userAdapter.retry() }
            )
            binding.btnRetry.setOnClickListener {
                userAdapter.retry()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun userSearch() {
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val svUsers = binding.svUsers
        svUsers.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        svUsers.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    binding.rvUser.scrollToPosition(0)
                    viewModel.searchUsers(query)
                    svUsers.clearFocus()


                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}
package com.example.anggorobenolukito.ui.list

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anggorobenolukito.R
import com.example.anggorobenolukito.core.utils.Constant
import com.example.anggorobenolukito.databinding.FragmentUserBinding
import com.example.anggorobenolukito.domain.model.UserModel
import com.example.anggorobenolukito.ui.adapter.UserLoadStateAdapter
import com.example.anggorobenolukito.ui.adapter.UserPagingAdapter
import com.example.anggorobenolukito.utils.Constant
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private val TAG = "Paging Fragment"
    private val viewModel: UserViewModel by viewModels()
    private val userAdapter: UserPagingAdapter = UserPagingAdapter()
    private var pressedTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.observe(viewLifecycleOwner, {
            userAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            binding.progressCircular.visibility = View.GONE
            showRvUser()
        })
        userSearch()
        userAdapter.addLoadStateListener { combinedLoadStates ->
            with(binding) {
                // Default State
                progressCircular.isVisible = combinedLoadStates.source.refresh is LoadState.Loading
                rvUser.isVisible = combinedLoadStates.source.refresh is LoadState.NotLoading
                tvEmptyStateDesc.isVisible =
                    combinedLoadStates.source.refresh is LoadState.Error
                ivEmptyState.isVisible = combinedLoadStates.source.refresh is LoadState.Error
                btnRetry.isVisible = combinedLoadStates.source.refresh is LoadState.Error

                // No Internet Connection State
                if (combinedLoadStates.source.refresh is LoadState.Error) {
                    ivEmptyState.setImageResource(R.drawable.ic_failed)
                    rvUser.isVisible = false
                    tvEmptyStateDesc.text = getString(R.string.something_went_wrong)
                    tvEmptyStateDesc.isVisible = true
                    btnRetry.isVisible = true
                }
                // Empty State Result
                if (combinedLoadStates.source.refresh is LoadState.NotLoading
                    && combinedLoadStates.append.endOfPaginationReached && userAdapter.itemCount < 1
                ) {
                    rvUser.isVisible = false
                    ivEmptyState.isVisible = true
                    tvEmptyStateDesc.text = getString(R.string.no_user_found)
                    tvEmptyStateDesc.isVisible = true
                }
            }


        }
        backPressClose()

    }
    //Back press Close App

    private fun backPressClose() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // handle back event
            if (pressedTime + 5000 > System.currentTimeMillis()) {
                activity?.finishAndRemoveTask()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.back_to_exit),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            pressedTime = System.currentTimeMillis()
        }
    }

    private fun showRvUser() {
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
            userAdapter.setOnItemCallback(object : UserPagingAdapter.OnItemClickCallback {
                override fun onItemClicked(data: UserModel) {
                    val bundle = Bundle()
                    bundle.putString(Constant.EXTRA_USER, data.login)
                    findNavController().navigate(
                        R.id.action_pagingFragment_to_detailUserFragment,
                        bundle
                    )
                }

            })
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
                return true
            }
        })
    }
}
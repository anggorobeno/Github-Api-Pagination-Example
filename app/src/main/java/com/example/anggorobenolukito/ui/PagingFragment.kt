package com.example.anggorobenolukito.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anggorobenolukito.adapter.UserAdapter
import com.example.anggorobenolukito.adapter.UserLoadStateAdapter
import com.example.anggorobenolukito.adapter.UserPagingAdapter
import com.example.anggorobenolukito.databinding.FragmentPagingBinding
import com.example.anggorobenolukito.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagingFragment : Fragment() {
    private var _binding : FragmentPagingBinding? = null
    private val binding get() = _binding!!
    private val TAG = "Paging Fragment"
    private val viewModel : PagingViewModel by viewModels()
    private val userAdapter : UserPagingAdapter = UserPagingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPagingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.observe(viewLifecycleOwner,{
            userAdapter.submitData(viewLifecycleOwner.lifecycle,it)
            showRvArticle()

        })
    }
    private fun showRvArticle() {
        with(binding.rvUser) {
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            this.adapter = userAdapter.withLoadStateHeaderAndFooter(
                header = UserLoadStateAdapter { userAdapter.retry() },
                footer = UserLoadStateAdapter { userAdapter.retry() }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
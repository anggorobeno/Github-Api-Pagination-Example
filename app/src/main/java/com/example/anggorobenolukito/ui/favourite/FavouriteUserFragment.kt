package com.example.anggorobenolukito.ui.favourite

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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anggorobenolukito.R
import com.example.anggorobenolukito.data.local.entity.DetailUserEntity
import com.example.anggorobenolukito.databinding.FragmentFavouriteUserBinding
import com.example.anggorobenolukito.ui.adapter.FavouriteUserAdapter
import com.example.anggorobenolukito.utils.Constant
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteUserFragment : Fragment() {
    private var _binding: FragmentFavouriteUserBinding? = null
    private val binding get() = _binding!!
    private val TAG = "Favourite Fragment"
    private val viewModel: FavouriteUserViewModel by viewModels()
    private val userAdapter: FavouriteUserAdapter = FavouriteUserAdapter()
    private var pressedTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouriteUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showFavouriteUser()
        itemTouchHelper.attachToRecyclerView(binding.rvUser)
        backPressClose()
        favouriteSearch()
    }

    private fun favouriteSearch() {
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val svUsers = binding.svUsers
        svUsers.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        svUsers.isSubmitButtonEnabled = false
        svUsers.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    binding.rvUser.scrollToPosition(0)
                    val searchQuery = "$newText"
                    viewModel.searchFavouriteUser(searchQuery).observe(viewLifecycleOwner, {
                        it.let {
                            userAdapter.submitList(it)
                            showRvUser()
                        }
                    })
                }
                return true
            }
        })
    }

    private fun backPressClose() {
        //Back press Close App
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
                    .show();
            }
            pressedTime = System.currentTimeMillis()
        }
    }

    private fun showFavouriteUser() {
        viewModel.getFavouriteUser().observe(viewLifecycleOwner, { favouriteUser ->
            if (favouriteUser != null) {
                userAdapter.submitList(null)
                userAdapter.submitList(favouriteUser)
                if (userAdapter.itemCount < 1) {
                    binding.ivEmptyState.isVisible = true
                    binding.tvEmptyStateDesc.text = getString(R.string.no_favourite_user)
                    binding.tvEmptyStateDesc.isVisible = true
                } else {
                    binding.ivEmptyState.isVisible = false
                    binding.tvEmptyStateDesc.isVisible = false
                }
                showRvUser()
            }


        })

    }

    private fun showRvUser() {
        binding.progressCircular.isVisible = false
        with(binding.rvUser) {
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            adapter = userAdapter
            userAdapter.setOnItemClick(object : FavouriteUserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: DetailUserEntity) {
                    val bundle = Bundle()
                    bundle.putString(Constant.EXTRA_USER, data.login)
                    findNavController().navigate(
                        R.id.action_favouriteUserFragment_to_detailUserFragment,
                        bundle
                    )
                }

            })
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val detailUserEntity = userAdapter.getSwipedData(swipedPosition)
                detailUserEntity?.let { viewModel.setFavouriteUser(it) }
                val snackbar =
                    Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) { v ->
                    detailUserEntity?.let { viewModel.setFavouriteUser(it) }
                }
                snackbar.show()
            }
        }
    })
}
package com.example.anggorobenolukito.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anggorobenolukito.R
import com.example.anggorobenolukito.adapter.FavouriteUserAdapter
import com.example.anggorobenolukito.data.local.entity.DetailUserEntity
import com.example.anggorobenolukito.databinding.FragmentFavouriteUserBinding
import com.example.anggorobenolukito.utils.Constant
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
        backPressClose()
    }

    private fun backPressClose() {
        //Back press Close App
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // handle back event
            if (pressedTime + 5000 > System.currentTimeMillis()) {
                activity?.finishAndRemoveTask()
            } else {
                Toast.makeText(requireContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            pressedTime = System.currentTimeMillis()
        }
    }

    private fun showFavouriteUser() {
        viewModel.getFavouriteUser().observe(viewLifecycleOwner, { favouriteUser ->
            if (favouriteUser != null) {
                userAdapter.setFavouriteUser(favouriteUser)
                Log.d(TAG, "showFavouriteUser: $favouriteUser")
                Log.d(TAG, "showFavouriteUser: ${binding.rvUser.isVisible}")
                if (userAdapter.itemCount < 1) {
                    binding.ivEmptyState.isVisible = true
                    binding.tvEmptyStateDesc.text = "No Favourite User"
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
}
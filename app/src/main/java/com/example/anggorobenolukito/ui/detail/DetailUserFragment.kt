package com.example.anggorobenolukito.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.anggorobenolukito.R
import com.example.anggorobenolukito.core.utils.Constant
import com.example.anggorobenolukito.core.utils.Status
import com.example.anggorobenolukito.databinding.FragmentDetailUserBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailUserFragment : Fragment() {
    private val TAG = "DetailUserFragment"
    private var _binding: FragmentDetailUserBinding? = null
    private val binding
        get() =
            _binding!!
    private val viewModel: DetailUserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = requireArguments().getString(Constant.EXTRA_USER)
        if (username != null) {
            showDetailUser(username)
            observeFavouriteUser()
        }
        binding.icBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeFavouriteUser() {
        viewModel.getDetail.observe(viewLifecycleOwner, { favouriteUser ->
            if (favouriteUser != null) {
                when (favouriteUser.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        if (favouriteUser.data != null) {
                            val state = favouriteUser.data.isFavourite
                            setFavouriteState(state)
                        }
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        favouriteUser.message?.let {
                            Snackbar.make(
                                requireView(),
                                it, Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }

            }
        })
    }

    private fun setFavouriteState(state: Boolean) {
        if (state) {
            binding.floatingActionButton.setImageResource(R.drawable.ic_favorite)
        } else {
            binding.floatingActionButton.setImageResource(R.drawable.ic_favourite_off)
        }
        binding.floatingActionButton.setOnClickListener {
            viewModel.setFavouriteUser()
            if (state) {
                Snackbar.make(
                    requireView(),
                    getString(R.string.delete_user_favourite),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Snackbar.make(
                    requireView(),
                    getString(R.string.add_user_favourite),
                    Snackbar.LENGTH_SHORT
                ).show()

            }
        }
    }

    private fun showDetailUser(username: String) {
        viewModel.setDetailUsername(username)
        viewModel.getDetail.observe(viewLifecycleOwner, { detailUser ->
            if (detailUser != null) {
                when (detailUser.status) {
                    Status.SUCCESS -> {
                        if (detailUser.data != null) {

                            with(binding) {
                                progressBar.visibility = View.GONE
                                Glide.with(requireContext())
                                    .load(detailUser.data.avatarUrl)
                                    .into(ivAvatar)
                                tvUserName.text = detailUser.data.login
                                tvType.text = detailUser.data.type
                                tvUserFollowers.text = detailUser.data.followers.toString()
                                tvUserFollowings.text = detailUser.data.following.toString()
                            }
                        }
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        detailUser.message?.let {
                            Snackbar.make(
                                requireView(),
                                it, Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
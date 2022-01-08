package com.example.anggorobenolukito.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.anggorobenolukito.databinding.FragmentDetailUserBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.anggorobenolukito.utils.Constant
import com.example.anggorobenolukito.utils.Status
import com.google.android.material.snackbar.Snackbar

@AndroidEntryPoint
class DetailUserFragment : Fragment() {
    private val TAG = "DetailUserFragment"
    private var _binding: FragmentDetailUserBinding? = null
    private val binding
        get() =
            _binding!!
    private val viewModel : DetailUserViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailUserBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = requireArguments().getString(Constant.EXTRA_USER)
        if (username != null) {
            showDetailUser(username)
        }
    }

    private fun showDetailUser(username: String) {
        viewModel.getDetailUser(username).observe(viewLifecycleOwner,{
            detailUser ->
            if (detailUser != null){
                when(detailUser.status){
                    Status.SUCCESS ->{

                        with(binding){
                            progressCircular.visibility = View.GONE
                            Glide.with(requireContext())
                                .load(detailUser.data?.avatarUrl)
                                .into(detailAvatar)
                            detailUsername.text = detailUser.data?.login
                            detailName.text = detailUser.data?.name
                            detailRepository.text = detailUser.data?.publicRepos.toString()
                            detailCompany.text = detailUser.data?.company
                            detailFollowers.text = detailUser.data?.followers.toString()
                            detailFollowing.text = detailUser.data?.following.toString()



                        }



                    }
                     Status.ERROR -> {
                         binding.progressCircular.visibility = View.GONE
                         detailUser.message?.let {
                             Snackbar.make(requireView(),
                                 it,Snackbar.LENGTH_SHORT).show()
                         }

                    }
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE

                    }
                }
            }
        })

    }
}
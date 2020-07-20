package com.silverpants.grocer.view.home.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.silverpants.grocer.R
import com.silverpants.grocer.databinding.FragmentHomeProfileBinding
import com.silverpants.grocer.network.Result
import com.silverpants.grocer.view.home.viewmodels.UserDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [HomeProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class HomeProfileFragment : Fragment(R.layout.fragment_home_profile) {

    private val viewModel: UserDetailsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentHomeProfileBinding.bind(view)
        viewModel.userDetailsLiveData.observe(viewLifecycleOwner, Observer {
            it?.let { result ->
                when (result) {
                    is Result.Success -> {
                        binding.txtUid.text = result.data?.uid
                    }
                }
            }
        })
    }


    companion object {
        @JvmStatic
        fun newInstance() = HomeProfileFragment()
    }
}

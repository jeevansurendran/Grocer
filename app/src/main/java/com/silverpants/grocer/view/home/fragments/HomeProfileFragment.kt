package com.silverpants.grocer.view.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.silverpants.grocer.network.legacy.Resource
import com.silverpants.grocer.databinding.FragmentHomeProfileBinding
import com.silverpants.grocer.view.home.viewmodels.UserDetailsViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [HomeProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeProfileFragment : Fragment() {

    private var _binding: FragmentHomeProfileBinding? = null

    private val viewModel: UserDetailsViewModel by activityViewModels()

    //this has valid states only between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.userDetails.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource) {
                    is Resource.Success -> {
                        binding.txtUid.text = resource.data?.uid
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

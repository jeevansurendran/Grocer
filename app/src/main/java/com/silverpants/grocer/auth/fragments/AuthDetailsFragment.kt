package com.silverpants.grocer.auth.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.silverpants.grocer.R
import com.silverpants.grocer.auth.viewmodels.AuthViewModel
import com.silverpants.grocer.data.resource.Resource
import com.silverpants.grocer.misc.toast
import kotlinx.android.synthetic.main.fragment_auth_details.view.*

/**
 * A simple [Fragment] subclass.
 */
class AuthDetailsFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()
    private val args: AuthDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.btn_auth_details_verify.setOnClickListener {
            val name = view.til_auth_details_name.editText?.text.toString()
            val number = args.number
            toast(" request being sent is ${name}, $number")
            authViewModel.registerUser(name, number);
        }

        authViewModel.registeredResult.observe(viewLifecycleOwner, Observer() {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        toast("${it.data?.name} ${it.data?.number}")

                    }
                    is Resource.Error -> {
                        toast(it.message ?: "Fuck")
                    }
                }
            }
        })
    }
}

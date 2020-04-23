package com.silverpants.grocer.auth.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.silverpants.grocer.R
import com.silverpants.grocer.auth.viewmodels.AuthViewModel
import com.silverpants.grocer.misc.parentActivityDelegate
import com.silverpants.grocer.misc.toast
import kotlinx.android.synthetic.main.fragment_auth_details.view.*

/**
 * A simple [Fragment] subclass.
 */
class AuthDetailsFragment : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()
    private val args: AuthDetailsFragmentArgs by navArgs()
    private val authInteractionListener: AuthInteractionListener by parentActivityDelegate()
    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                if (authViewModel.authState.value == AuthViewModel.STATES.STATE_SIGN_IN_REGISTER) {
                    toast("he took the number got an otp and then decided to quit what a fucker")
                    findNavController().popBackStack(R.id.auth_phone_fragment, true)
                    authViewModel.updateState(AuthViewModel.STATES.STATE_INITIALIZED)
                }
            }
        }
    private lateinit var tilNameField: TextInputLayout
    private lateinit var btnRegister: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tilNameField = view.til_auth_details_name
        btnRegister = view.btn_auth_details_verify
        setupBackPressedCallbacks()
        btnRegister.setOnClickListener {
            val name = tilNameField.editText?.text.toString()
            if(name.isBlank()) {
                toast("Enter a valid name")
                return@setOnClickListener
            }
            authViewModel.updateName(name)
            authInteractionListener.registerWithNumber()
        }



    }

    private fun setupBackPressedCallbacks() {
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    interface AuthInteractionListener {
        fun registerWithNumber()
    }
}

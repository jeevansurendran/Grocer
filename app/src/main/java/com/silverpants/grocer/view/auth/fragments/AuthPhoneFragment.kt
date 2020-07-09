package com.silverpants.grocer.view.auth.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.silverpants.grocer.R
import com.silverpants.grocer.misc.Utils
import kotlinx.android.synthetic.main.fragment_auth_phone.view.*

/**
 * A simple [Fragment] subclass to take the mobile number
 */
class AuthPhoneFragment : Fragment() {
    private lateinit var tilPhoneField: TextInputLayout
    private val navController: NavController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth_phone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.tv_auth_phone_terms_and_conditions.text =
            Utils.fromHtml(getString(R.string.terms_and_condition))


        tilPhoneField = view.til_auth_phone_phone
        tilPhoneField.editText?.doAfterTextChanged {
            if(!tilPhoneField.error.isNullOrEmpty()) tilPhoneField.error= null
        }

        view.btn_auth_phone_verify.setOnClickListener {
            if(!validatePhoneNumber()) {
                return@setOnClickListener
            }
            val action = AuthPhoneFragmentDirections.verifyOtp(tilPhoneField.editText?.text.toString())
            navController.navigate(action)
        }
    }

    private fun validatePhoneNumber(): Boolean {
        val number = tilPhoneField.editText?.text.toString()
        if (number.isBlank()) {
            tilPhoneField.error = getString(R.string.auth_phone_error_blank)
            return false
        }
        if (!getString(R.string.regex_phone_india).toRegex().matches(number)) {
            tilPhoneField.error = getString(R.string.auth_phone_error_invalid)
            return false
        }
        return true
    }

}

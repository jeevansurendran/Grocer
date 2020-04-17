package com.silverpants.grocer.auth.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.silverpants.grocer.R
import kotlinx.android.synthetic.main.fragment_auth_otp.view.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AuthOtpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth_otp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.btn_auth_otp_verify.setOnClickListener {
            findNavController().navigate(R.id.take_user_details)
        }
    }
}

package com.silverpants.grocer.view.auth.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.silverpants.grocer.R
import com.silverpants.grocer.view.auth.callbacks.PhoneAuthenticator
import com.silverpants.grocer.view.auth.viewmodels.AuthViewModel
import com.silverpants.grocer.misc.Constants
import com.silverpants.grocer.misc.Utils
import com.silverpants.grocer.misc.parentActivityDelegate
import com.silverpants.grocer.misc.toast
import kotlinx.android.synthetic.main.fragment_auth_otp.view.*


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AuthOtpFragment : Fragment() {

    private val args: AuthOtpFragmentArgs by navArgs()
    private val authViewModel: AuthViewModel by activityViewModels()
    private val authInteractionListener: AuthInteractionListener by parentActivityDelegate()
    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                if (authViewModel.authState.value == AuthViewModel.STATES.STATE_INITIALIZED) {
                    toast("wow this could happen")
                    findNavController().popBackStack()
                }
            }
        }

    private val phoneAuthenticator: PhoneAuthenticator by lazy {
        object : PhoneAuthenticator(requireActivity(), args.number) {
            override fun onVerificationSuccess(credential: PhoneAuthCredential) {
                authViewModel.updateState(AuthViewModel.STATES.STATE_VERIFY_SUCCESS)
                authViewModel.updateNumber(number)
                authInteractionListener.authenticateWithNumberCredentials(credential)
            }

            override fun onVerificationError(exception: FirebaseException) {
                authViewModel.updateState(AuthViewModel.STATES.STATE_VERIFY_FAILED)
                when (exception) {
                    is FirebaseTooManyRequestsException -> {
                        toast("Too many login requests from this number, try again later")
                    }
                    is FirebaseAuthInvalidCredentialsException -> {
                        toast("The given credentials are invalid")
                        authViewModel.updateState(AuthViewModel.STATES.STATE_INITIALIZED)
                    }

                }
            }

            override fun onStartTimeOut() {
                authViewModel.updateState(AuthViewModel.STATES.STATE_CODE_SENT)

            }
        }

    }
    private val timer = object : CountDownTimer(Constants.AUTH_OTP_DEFAULT_TIMEOUT * 1000, 1000) {
        override fun onFinish() {
            authViewModel.updateState(AuthViewModel.STATES.STATE_INITIALIZED)
        }

        override fun onTick(millisUntilFinished: Long) {
            val min = millisUntilFinished / 1000 / 60
            val sec = millisUntilFinished / 1000 - min * 60
            tilOtpField.helperText = "OTP can be resent in ${Utils.formatTime(min, sec)}"
        }
    }
    private lateinit var tilOtpField: TextInputLayout
    private lateinit var btnVerify: Button
    private lateinit var btnResend: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth_otp, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tilOtpField = view.til_auth_otp_otp
        btnResend = view.btn_auth_otp_resend
        btnVerify = view.btn_auth_otp_verify
        setupObservers()
        setupBackPressedCallbacks()
        //Starts the otp verification
        if (authViewModel.authState.value == AuthViewModel.STATES.STATE_INITIALIZED) {
            authViewModel.updateState(AuthViewModel.STATES.STATE_VERIFY_START)
        }
        btnResend.setOnClickListener {
            authViewModel.updateState(AuthViewModel.STATES.STATE_VERIFY_RESTART)
        }
        btnVerify.setOnClickListener {
            val otpCode = tilOtpField.editText?.text.toString()
            if (otpCode.length != 6) {
                toast("Invalid Otp, Try Again")
                return@setOnClickListener
            }
            phoneAuthenticator.verifyOtpCode(otpCode)
        }
    }

    private fun setupBackPressedCallbacks() {
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun setupObservers() {
        authViewModel.authState.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    AuthViewModel.STATES.STATE_VERIFY_START -> {
                        phoneAuthenticator.startOtpVerification()
                        btnResend.isEnabled = false
                        btnVerify.isEnabled = false
                        onBackPressedCallback.isEnabled = false
                    }
                    AuthViewModel.STATES.STATE_VERIFY_RESTART -> {
                        phoneAuthenticator.resendVerificationCode()
                        btnResend.isEnabled = false
                        btnVerify.isEnabled = false
                        onBackPressedCallback.isEnabled = false
                    }
                    AuthViewModel.STATES.STATE_CODE_SENT -> {
                        timer.start()
                        btnVerify.isEnabled = true
                        btnResend.isEnabled = false
                        onBackPressedCallback.isEnabled = false
                    }
                    AuthViewModel.STATES.STATE_SIGN_IN_REGISTER-> {
                        val action = AuthOtpFragmentDirections.takeUserDetails(number = args.number)
                        findNavController().navigate(action)
                    }
                    else -> {
                        btnResend.isEnabled = true
                        btnVerify.isEnabled = true
                        tilOtpField.helperText = null
                        timer.cancel()
                        onBackPressedCallback.isEnabled = true
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    interface AuthInteractionListener {
        fun authenticateWithNumberCredentials(credential: PhoneAuthCredential)
    }
}

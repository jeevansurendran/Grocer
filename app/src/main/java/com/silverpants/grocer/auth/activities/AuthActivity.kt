package com.silverpants.grocer.auth.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.silverpants.grocer.R
import com.silverpants.grocer.auth.fragments.AuthDetailsFragment
import com.silverpants.grocer.auth.fragments.AuthOtpFragment
import com.silverpants.grocer.auth.viewmodels.AuthViewModel
import com.silverpants.grocer.data.resource.Resource
import com.silverpants.grocer.misc.toast

class AuthActivity : AppCompatActivity(), AuthOtpFragment.AuthInteractionListener, AuthDetailsFragment.AuthInteractionListener {
    private val authViewModel: AuthViewModel by viewModels()
    private val auth: FirebaseAuth get() = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        setupObserver()
    }

    private fun setupObserver() {
        authViewModel.authState.observe(this, Observer {
            it?.let {
                when (it) {
                    AuthViewModel.STATES.STATE_SIGN_IN_LOGIN-> {
                        loginUser()
                    }
                    AuthViewModel.STATES.STATE_SIGN_IN_SUCCESS-> {
                        successAuth()
                        finish()
                    }
                }
            }
        })
        authViewModel.authResult.observe(this, Observer {
            it?.let {
                when(it) {
                    is Resource.Success -> {
                        authViewModel.updateState(AuthViewModel.STATES.STATE_SIGN_IN_SUCCESS)
                    }
                    is Resource.InvalidRequest-> {
                        authViewModel.updateState(AuthViewModel.STATES.STATE_SIGN_IN_REGISTER)
                    }
                }
            }
        })
    }

    private fun successAuth() {
        toast("Successfully logged in")
    }

    private fun loginUser() {
        authViewModel.loginUser()
    }

    private fun registerUser() {
       authViewModel.registerUser()
    }

    override fun authenticateWithNumberCredentials(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    user?.run {
                        getIdToken(true).addOnCompleteListener {
                            if (task.isSuccessful) {
                                authViewModel.updateIdToken(it.result?.token)
                                authViewModel.updateState(AuthViewModel.STATES.STATE_SIGN_IN_LOGIN)
                            }
                        }
                    }
                } else {
                    authViewModel.updateState(AuthViewModel.STATES.STATE_SIGN_IN_FAILED)
                }
            }
    }

    override fun onBackPressed() {
        if (authViewModel.shouldBlockExit) {
            return
        }
        super.onBackPressed()
    }

    override fun registerWithNumber() {
        registerUser()
    }
}

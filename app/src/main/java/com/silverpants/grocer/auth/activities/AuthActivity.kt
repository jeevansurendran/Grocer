package com.silverpants.grocer.auth.activities

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.PhoneAuthCredential
import com.silverpants.grocer.R
import com.silverpants.grocer.auth.fragments.AuthOtpFragment
import com.silverpants.grocer.auth.viewmodels.AuthViewModel
import com.silverpants.grocer.misc.toast

import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity(), AuthOtpFragment.AuthInteractionListener {
    private val authViewModel:AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    override fun authenticateWithNumberCredentials(credential: PhoneAuthCredential) {
        Thread.sleep(5000)
        toast("Seems like it has authenticated successfully i am going to head out now ")
    }

    override fun onBackPressed() {
        if(authViewModel.shouldBlockExit) {
            return
        }
        super.onBackPressed()
    }
}

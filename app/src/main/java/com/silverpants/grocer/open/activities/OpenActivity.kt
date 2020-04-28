package com.silverpants.grocer.open.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.silverpants.grocer.R
import com.silverpants.grocer.data.network.WebApiClient
import com.silverpants.grocer.data.resource.Resource
import com.silverpants.grocer.home.activities.HomeActivity
import com.silverpants.grocer.misc.toast
import com.silverpants.grocer.open.viewmodels.OpenViewModel

/**
 * The [OpenActivity] displays the logo and opens into the [HomeActivity]
 *
 * @author @jeevansurendran
 * @since 1.0
 */
class OpenActivity : AppCompatActivity() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val viewModel: OpenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open)

        viewModel.authResult.observe(this, Observer {
            it?.let {
                when (it) {
                    is Resource.Success -> {
                        toast("registered wow");
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        })
    }


    override fun onStart() {
        super.onStart()
        val intent = Intent(this, HomeActivity::class.java)
        Thread.sleep(2000)
        val onCompleteListener = OnCompleteListener<GetTokenResult> {
            if (it.isSuccessful) {
                WebApiClient.idToken = it.result?.token
                startActivity(intent)
                finish()
            } else {
                toast("${it.exception?.message}")
            }
        }
        val user = firebaseAuth.currentUser
        if (user == null) {
            firebaseAuth.signInAnonymously().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.user?.getIdToken(false)?.addOnCompleteListener {
                        viewModel.guestRegisterUser(it.result?.token!!)
                    }
                }
            }
        } else {
            toast("hmmmm i am an user wtf?")
            user.getIdToken(false).addOnCompleteListener(onCompleteListener)
        }
    }
}

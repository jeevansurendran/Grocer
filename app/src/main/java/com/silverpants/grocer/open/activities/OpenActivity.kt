package com.silverpants.grocer.open.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.silverpants.grocer.R
import com.silverpants.grocer.home.activities.HomeActivity
import com.silverpants.grocer.misc.toast
import com.silverpants.grocer.network.WebApiClient
import com.silverpants.grocer.network.coflow.Result
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

        val intent = Intent(this, HomeActivity::class.java)
        setupObservers()
        Thread.sleep(2000)
        val user = firebaseAuth.currentUser
        if (user == null) {
            firebaseAuth.signInAnonymously().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.user?.getIdToken(false)?.addOnCompleteListener {
                        viewModel.registerGuest(it.result?.token!!)
                    }
                }
            }
        } else {
            startActivity(intent)
            finish()
        }
    }
    private fun setupObservers() {
        val intent = Intent(this, HomeActivity::class.java)

        viewModel.authResult.observe(this, Observer {
            it?.let {
                when (it) {
                    is Result.Success -> {
                        toast("registered wow")
                        startActivity(intent)
                        finish()
                    }
                }
            }
        })
    }


    companion object {
        val TAG = OpenActivity::class.simpleName
    }

}

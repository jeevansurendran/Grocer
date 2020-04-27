package com.silverpants.grocer.home

import android.accounts.AccountManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.silverpants.grocer.MainActivity
import com.silverpants.grocer.R
import com.silverpants.grocer.auth.activities.AuthActivity

/**
 * The [OpenActivity] displays the logo and opens into the [MainActivity]
 *
 * @author @jeevansurendran
 * @since 1.0
 */
class OpenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open)
    }

    override fun onStart() {
        super.onStart()
        Thread.sleep(2000)
        val user = FirebaseAuth.getInstance().currentUser
        if(user == null) {
            val accountManager = AccountManager.get(this)


        }
        finish()
    }
}

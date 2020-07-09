package com.silverpants.grocer.view.onboarding.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.silverpants.grocer.R
import com.silverpants.grocer.misc.toast
import com.silverpants.grocer.network.coflow.Result
import com.silverpants.grocer.view.home.activities.HomeActivity
import com.silverpants.grocer.view.onboarding.viewmodels.OnBoardingViewModel
import timber.log.Timber

/**
 * The [OnBoardingActivity] displays the logo and opens into the [HomeActivity]
 *
 * @author @jeevansurendran
 * @since 1.0
 */
class OnBoardingActivity : AppCompatActivity() {
    val viewModel: OnBoardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open)

        viewModel.authResultLiveData.observe(this, Observer {
            it?.let {
                when (it) {
                    is Result.Success -> {
                        startActivity(Intent(baseContext, HomeActivity::class.java))
                        finish()
                    }
                    is Result.Error -> {
                        it.exception?.message?.let { it1 -> toast(it1) }
                    }
                    else -> {
                        Timber.d("Loading or something else")
                        it.exception?.message?.let { it1 -> toast(it1) }
                    }
                }
            }
        })
    }

    companion object {
        val TAG = OnBoardingActivity::class.simpleName
    }

}

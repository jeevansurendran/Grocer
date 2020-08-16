package com.silverpants.grocer.view.home.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.navArgs
import com.silverpants.grocer.R
import com.silverpants.grocer.databinding.FragmentNetworkErrorBinding
import com.silverpants.grocer.misc.base.RefreshableFragment
import java.io.Serializable


/**
 * why are you so annoying
 *
 * @author  @jeevansurendran
 * @since 1.0
 */
class NetworkErrorFragment :
    RefreshableFragment(R.layout.fragment_network_error) {

    private val args: NetworkErrorFragmentArgs by navArgs()

    var callback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentNetworkErrorBinding.bind(view)
        binding.btnNetworkTryAgain.setOnClickListener(args.tryAgainListener::tryAgain)
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    override fun onDestroy() {
        super.onDestroy()
        callback.remove()
    }
}


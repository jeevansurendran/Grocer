package com.silverpants.grocer.view.home.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.silverpants.grocer.R
import com.silverpants.grocer.databinding.FragmentHomeShopsBinding
import com.silverpants.grocer.misc.base.RefreshableFragment
import com.silverpants.grocer.misc.toast
import com.silverpants.grocer.network.Result
import com.silverpants.grocer.network.fragments.NetworkErrorFragment
import com.silverpants.grocer.view.home.epoxy.homeHeadingWithHolder
import com.silverpants.grocer.view.home.epoxy.homeShopWithHolder
import com.silverpants.grocer.view.home.listeners.CreateOrderListener
import com.silverpants.grocer.view.home.viewmodels.ShopsViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [HomeShopsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class HomeShopsFragment : RefreshableFragment(R.layout.fragment_home_shops),
    NetworkErrorFragment.TryAgainListener, CreateOrderListener {

    private val shopsViewModel: ShopsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentHomeShopsBinding.bind(view)
        val epoxyNearby = binding.epoxyNearbyShops

        epoxyNearby.withModels {
            homeHeadingWithHolder {
                id("shop heading")
                heading(resources.getString(R.string.home_shop_heading))
            }
            shopsViewModel.nearbyShopData.value?.data.takeIf { !it.isNullOrEmpty() }
                ?.forEachIndexed { index, shopModel ->
                    homeShopWithHolder {
                        id("shop $index")
                        shopModel(shopModel)
                        createOrderListener(this@HomeShopsFragment)
                    }
                }
        }
        shopsViewModel.nearbyShopData.observe(viewLifecycleOwner, Observer {
            it?.let {
                handleLoadingRefresh(it)
                when (it) {
                    is Result.Success -> {
                        epoxyNearby.requestModelBuild()

                    }
                    is Result.Error -> {
                        toast(it.exception?.message!!)
                        val action = HomeShopsFragmentDirections.networkError(this)
                        findNavController().navigate(action)
                    }
                    else -> {

                    }
                }
            }
        })
        initRefreshScreen(R.id.srl_home_shops)
    }

    override fun tryAgain(v: View) {
        shopsViewModel.refresh()
        findNavController().popBackStack()
    }

    override fun updateScreen() {
        super.updateScreen()
        shopsViewModel.refresh()
    }

    override fun createOrder(shopId: String) {
        val action = HomeShopsFragmentDirections.createOrder(shopId)
        findNavController().navigate(action)
    }
}

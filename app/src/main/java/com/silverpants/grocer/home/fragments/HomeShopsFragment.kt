package com.silverpants.grocer.home.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.silverpants.grocer.R
import com.silverpants.grocer.databinding.FragmentHomeShopsBinding
import com.silverpants.grocer.home.epoxy.shopModelWithHolder
import com.silverpants.grocer.home.viewmodels.ShopsViewModel
import com.silverpants.grocer.misc.base.BaseFragment
import com.silverpants.grocer.misc.toast
import com.silverpants.grocer.network.coflow.Result
import com.silverpants.grocer.network.legacy.Resource
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 * Use the [HomeShopsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeShopsFragment : BaseFragment(R.layout.fragment_home_shops) {

    private val shopsViewModel: ShopsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentHomeShopsBinding.bind(view)

        val epoxyNearby = binding.epoxyNearbyShops

        epoxyNearby.withModels {
            shopsViewModel.nearbyShopData.value?.data.takeIf { !it.isNullOrEmpty() }
                ?.forEachIndexed { index, shopModel ->
                    shopModelWithHolder {
                        id(index)
                        shopModel(shopModel)
                    }
                }
        }
        shopsViewModel.nearbyShopData.observe(viewLifecycleOwner, Observer {
            it?.let{
                when(it) {
                    is Result.Success-> {
                        epoxyNearby.requestModelBuild()
                    }
                    is Result.Error-> {
                        Timber.e(it.exception)
                    }
                }
            }

        })
    }
}

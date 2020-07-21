package com.silverpants.grocer.view.order.fragments

import android.os.Bundle
import android.view.View
import com.silverpants.grocer.R
import com.silverpants.grocer.databinding.FragmentOrderCreateBinding
import com.silverpants.grocer.misc.base.RefreshableFragment

class OrderCreateFragment : RefreshableFragment(R.layout.fragment_order_create) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentOrderCreateBinding.bind(view)
    }
}
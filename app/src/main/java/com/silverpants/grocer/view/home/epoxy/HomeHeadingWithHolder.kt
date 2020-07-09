package com.silverpants.grocer.view.home.epoxy

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.silverpants.grocer.R
import com.silverpants.grocer.databinding.ItemHomeHeadingBinding
import com.silverpants.grocer.misc.base.BaseEpoxyModelWithHolder

@EpoxyModelClass(layout = R.layout.item_home_heading)
abstract class HomeHeadingWithHolder : BaseEpoxyModelWithHolder<ItemHomeHeadingBinding>() {

    @EpoxyAttribute
    internal lateinit var heading:String


    override fun ItemHomeHeadingBinding.bind() {
        tvShopHeading.text = heading
    }
}
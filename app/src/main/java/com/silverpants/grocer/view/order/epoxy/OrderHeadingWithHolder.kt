package com.silverpants.grocer.view.order.epoxy

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.silverpants.grocer.R
import com.silverpants.grocer.databinding.ItemOrderDetailsHeadingBinding
import com.silverpants.grocer.misc.base.BaseEpoxyModelWithHolder

@EpoxyModelClass(layout = R.layout.item_order_details_heading)
abstract class OrderHeadingWithHolder : BaseEpoxyModelWithHolder<ItemOrderDetailsHeadingBinding>() {
    @EpoxyAttribute
    var heading: String? = null

    override fun ItemOrderDetailsHeadingBinding.bind() {
        tvOrderDetailsHeading.text = heading
    }
}
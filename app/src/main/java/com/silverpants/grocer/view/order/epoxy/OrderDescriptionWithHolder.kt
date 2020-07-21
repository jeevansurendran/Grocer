package com.silverpants.grocer.view.order.epoxy

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.silverpants.grocer.R
import com.silverpants.grocer.databinding.ItemOrderDetailsDescriptionBinding
import com.silverpants.grocer.misc.base.BaseEpoxyModelWithHolder


@EpoxyModelClass(layout = R.layout.item_order_details_description)
abstract class OrderDescriptionWithHolder :
    BaseEpoxyModelWithHolder<ItemOrderDetailsDescriptionBinding>() {

    @EpoxyAttribute
    var text: String? = null

    @EpoxyAttribute
    var description: String? = null

    override fun ItemOrderDetailsDescriptionBinding.bind() {
        tvOrderDetailsText.text = text
        tvOrderDetailsDescription.text = description
    }
}
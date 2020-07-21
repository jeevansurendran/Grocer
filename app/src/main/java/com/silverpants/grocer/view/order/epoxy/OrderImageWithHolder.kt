package com.silverpants.grocer.view.order.epoxy

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.silverpants.grocer.R
import com.silverpants.grocer.databinding.ItemOrderDetailsImageBinding
import com.silverpants.grocer.misc.Utils
import com.silverpants.grocer.misc.base.BaseEpoxyModelWithHolder
import java.io.File

@EpoxyModelClass(layout = R.layout.item_order_details_image)
abstract class OrderImageWithHolder : BaseEpoxyModelWithHolder<ItemOrderDetailsImageBinding>() {
    @EpoxyAttribute
    internal lateinit var imageFile: File

    @EpoxyAttribute
    internal lateinit var imageUrl: String

    override fun ItemOrderDetailsImageBinding.bind() {
        if (this@OrderImageWithHolder::imageUrl.isInitialized) {
            Utils.loadImageFromUrlWithPlaceHolder(
                imOrderDetailsImage,
                imageUrl,
                R.drawable.xml_order_image_placeholder
            )
        } else if (this@OrderImageWithHolder::imageFile.isInitialized) {
            Utils.loadImageFromFileWithPlaceHolder(
                imOrderDetailsImage,
                imageFile,
                R.drawable.xml_order_image_placeholder
            )
        }
    }

}
package com.silverpants.grocer.view.camera.epoxy

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.silverpants.grocer.view.camera.listener.AddImageListener
import com.silverpants.grocer.R
import com.silverpants.grocer.databinding.ItemCameraCaptureAddBinding
import com.silverpants.grocer.misc.base.BaseEpoxyModelWithHolder

@EpoxyModelClass(layout = R.layout.item_camera_capture_add)
abstract class CameraCaptureAddWithHolder :
    BaseEpoxyModelWithHolder<ItemCameraCaptureAddBinding>() {

    @EpoxyAttribute
    internal lateinit var onAddMoreImage: AddImageListener

    override fun ItemCameraCaptureAddBinding.bind() {
        btnCameraCaptureAdd.setOnClickListener {
            onAddMoreImage.addImage()
        }
    }

}
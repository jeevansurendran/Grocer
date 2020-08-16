package com.silverpants.grocer.view.camera.epoxy

import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.silverpants.grocer.view.camera.listener.AddImageListener
import com.silverpants.grocer.R
import com.silverpants.grocer.databinding.ItemCameraCaptureImageBinding
import com.silverpants.grocer.misc.Utils
import com.silverpants.grocer.misc.base.BaseEpoxyModelWithHolder
import java.io.File

@EpoxyModelClass(layout = R.layout.item_camera_capture_image)
abstract class CameraCaptureImageWithHolder :
    BaseEpoxyModelWithHolder<ItemCameraCaptureImageBinding>() {

    @EpoxyAttribute
    internal lateinit var imageFile: File

    @EpoxyAttribute
    var index: Int = 0

    @EpoxyAttribute
    internal lateinit var imageClickListener: AddImageListener

    override fun ItemCameraCaptureImageBinding.bind() {
        Utils.loadImageFromFile(imCameraCaptureImage, imageFile)
        imCameraCaptureImage.setOnClickListener {
            imageClickListener.openGalleryPreview(index)
        }
    }

}
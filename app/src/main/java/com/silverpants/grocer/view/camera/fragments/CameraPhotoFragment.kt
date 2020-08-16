package com.silverpants.grocer.view.camera.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.silverpants.grocer.misc.Utils
import java.io.File

/** Fragment used for each individual page showing a photo inside of [GalleryFragment] */
class CameraPhotoFragment internal constructor() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ImageView = ImageView(context)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments ?: return
        val resource = File(args.getString(FILE_NAME_KEY)!!)
        Utils.loadImageFromFile(view as ImageView, resource)
    }

    companion object {
        private const val FILE_NAME_KEY = "file_name"

        fun create(image: File) = CameraPhotoFragment().apply {
            arguments = Bundle().apply {
                putString(FILE_NAME_KEY, image.absolutePath)
            }
        }
    }
}
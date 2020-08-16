package com.silverpants.grocer.view.camera.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.silverpants.grocer.view.camera.viewmodels.CameraCaptureViewModel
import com.silverpants.grocer.R
import com.silverpants.grocer.databinding.FragmentCameraCaptureBinding
import com.silverpants.grocer.hardware.network.Result
import com.silverpants.grocer.view.camera.epoxy.cameraCaptureAddWithHolder
import com.silverpants.grocer.view.camera.epoxy.cameraCaptureImageWithHolder
import com.silverpants.grocer.view.camera.listener.AddImageListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CameraCaptureFragment : Fragment(R.layout.fragment_camera_capture), AddImageListener {

    val viewModel: CameraCaptureViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentCameraCaptureBinding.bind(view)

        binding.epoxyCameraCapture.withModels {

            viewModel.imageListLiveData.value?.let {
                when (it) {
                    is Result.Success -> {
                        it.data?.forEachIndexed { index, image ->
                            cameraCaptureImageWithHolder {
                                id("A$index")
                                imageFile(image)
                                index(index)
                                imageClickListener(this@CameraCaptureFragment)
                            }
                        }
                        cameraCaptureAddWithHolder {
                            id("Add")
                            onAddMoreImage(this@CameraCaptureFragment)
                        }
                    }
                    else -> {

                    }
                }
            }
        }

        viewModel.imageListLiveData.observe(viewLifecycleOwner, Observer {
            binding.epoxyCameraCapture.requestModelBuild();
        })
    }

    override fun addImage() {
//        val action = CameraCaptureFragmentDirections.addMorePicture()
//        findNavController().navigate(action)
    }

    override fun openGalleryPreview(index: Int) {
//        val action = CameraCaptureFragmentDirections.showPictures(index)
//        findNavController().navigate(action)
    }
}
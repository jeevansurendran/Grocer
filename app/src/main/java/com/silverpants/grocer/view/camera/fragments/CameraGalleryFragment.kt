package com.silverpants.grocer.view.camera.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.silverpants.grocer.view.camera.viewmodels.CameraCaptureViewModel
import com.silverpants.grocer.R
import com.silverpants.grocer.databinding.FragmentCameraGalleryBinding
import com.silverpants.grocer.hardware.network.Result
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


/**
 *
 * a image preview before
 */
@AndroidEntryPoint
class CameraGalleryFragment : Fragment(R.layout.fragment_camera_gallery) {

    private lateinit var mediaList: Array<File>
    private val viewModel: CameraCaptureViewModel by activityViewModels()
    private  val args : CameraGalleryFragmentArgs by navArgs()

    inner class MediaPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getCount(): Int = mediaList.size
        override fun getItem(position: Int): Fragment = CameraPhotoFragment.create(mediaList[position])
        override fun getItemPosition(obj: Any): Int = POSITION_NONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.imageListLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                when(it) {
                    is Result.Success-> {
                        mediaList = it.data!!
                    }
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentCameraGalleryBinding.bind(view)
        if (mediaList.isEmpty()) {
            binding.btnCameraGalleryDelete.isEnabled = false
        }

        val mediaViewPager = binding.photoViewPager.apply {
            offscreenPageLimit = 2
            adapter = MediaPagerAdapter(childFragmentManager)
        }

        mediaViewPager.setCurrentItem(args.default, false)
    }



}
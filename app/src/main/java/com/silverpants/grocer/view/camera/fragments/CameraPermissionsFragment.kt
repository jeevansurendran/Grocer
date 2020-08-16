package com.silverpants.grocer.view.camera.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.silverpants.grocer.R

class CameraPermissionsFragment : Fragment(R.layout.fragment_camera_permissions) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (hasPermissions(requireContext())) {
            starCamera()
        } else {
            requestPermissions(
                REQUIRED_PERMISSIONS,
                PERMISSION_REQUEST_CAMERA
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (hasPermissions(requireContext())) {
                starCamera()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun starCamera() {
//        val action = CameraPermissionsFragmentDirections.startCamera()
//        findNavController().navigate(action)
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val PERMISSION_REQUEST_CAMERA = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

        /** Convenience method used to check if all permissions required by this app are granted */
        fun hasPermissions(context: Context) = REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }
}
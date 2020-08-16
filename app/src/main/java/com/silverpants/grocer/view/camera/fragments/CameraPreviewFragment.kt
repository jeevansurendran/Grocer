package com.silverpants.grocer.view.camera.fragments

import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.silverpants.grocer.view.camera.util.LuminosityAnalyzer
import com.silverpants.grocer.R
import com.silverpants.grocer.databinding.FragmentCameraPreviewBinding
import com.silverpants.grocer.domain.orders.CreateMenuOrderSuspendUseCase
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


/**
 * Preview of the image and makes it look amazing.
 */
@AndroidEntryPoint
class CameraPreviewFragment : Fragment(R.layout.fragment_camera_preview) {

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var viewFinder: PreviewView
    private lateinit var cameraFlashButton: ImageButton
    private lateinit var cameraCaptureButton: ImageButton

    val orderId = "1000"
   @Inject lateinit var  createImageForOrder: CreateMenuOrderSuspendUseCase

    private var flashMode: Int = ImageCapture.FLASH_MODE_OFF
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK
    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentCameraPreviewBinding.bind(view)
        viewFinder = binding.pvViewFinder
        cameraFlashButton = binding.cameraFlashButton
        cameraCaptureButton = binding.cameraCaptureButton

        cameraExecutor = Executors.newSingleThreadExecutor()

        viewFinder.post {
            updateCameraUi()
            setUpCamera()
        }
    }

    private fun updateCameraUi() {
        cameraFlashButton.setOnClickListener {
            when (flashMode) {
                ImageCapture.FLASH_MODE_OFF -> {
                    flashMode = ImageCapture.FLASH_MODE_ON
                    cameraFlashButton.setImageResource(R.drawable.xml_camera_flash_on)
                }
                ImageCapture.FLASH_MODE_ON -> {
                    flashMode = ImageCapture.FLASH_MODE_AUTO
                    cameraFlashButton.setImageResource(R.drawable.xml_camera_flash_auto)
                }
                ImageCapture.FLASH_MODE_AUTO -> {
                    flashMode = ImageCapture.FLASH_MODE_OFF
                    cameraFlashButton.setImageResource(R.drawable.xml_camera_flash_off)
                }
            }
            imageCapture?.flashMode = flashMode
        }

        cameraCaptureButton.setOnClickListener {
            imageCapture?.let { imageCapture ->
                val photoFile = createImageForOrder(orderId).data!!

                val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile)
                    .build()

                imageCapture.takePicture(
                    outputOptions, cameraExecutor, object : ImageCapture.OnImageSavedCallback {
                        override fun onError(exc: ImageCaptureException) {
                            Timber.e("Photo capture failed: ${exc.message}")
                        }

                        override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                            val savedUri = output.savedUri ?: Uri.fromFile(photoFile)
                            Timber.d("Photo capture succeeded: $savedUri")
                            requireActivity().runOnUiThread {
                                Toast.makeText(
                                    requireContext(),
                                    "Image at $savedUri",
                                    Toast.LENGTH_LONG
                                ).show()

                                showPreview()
                            }
                        }
                    })
            }
        }
    }

    fun showPreview() {
//        val action = CameraPreviewFragmentDirections.showImage()
//        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()
        if (!CameraPermissionsFragment.hasPermissions(requireContext())) {
//            val action = CameraPreviewFragmentDirections.askPermission()
//            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        cameraExecutor.shutdown()
    }


    private fun setUpCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(Runnable {
            cameraProvider = cameraProviderFuture.get()

            lensFacing = when {
                hasBackCamera() -> CameraSelector.LENS_FACING_BACK
                hasFrontCamera() -> CameraSelector.LENS_FACING_FRONT
                else -> throw IllegalStateException("Back and front camera are unavailable")
            }

            bindCameraUseCases()

        }, ContextCompat.getMainExecutor(requireContext()))
        cameraProviderFuture.isDone
    }

    private fun bindCameraUseCases() {
        val cameraProvider = cameraProvider
            ?: throw IllegalStateException("Camera initialization failed.")

        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

        val metrics = DisplayMetrics().also { viewFinder.display.getRealMetrics(it) }
        Timber.d( "Screen metrics: ${metrics.widthPixels} x ${metrics.heightPixels}")

        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)

        // Preview
        preview = Preview.Builder()
            // We request aspect ratio but no resolution
            .setTargetAspectRatio(screenAspectRatio)
            .build()

        // ImageCapture
        imageCapture = getImageCapture()

        // ImageAnalysis
        imageAnalyzer = ImageAnalysis.Builder()
            // We request aspect ratio but no resolution
            .setTargetAspectRatio(screenAspectRatio)
            .build()
            // The analyzer can then be assigned to the instance
            .also {
                it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
                    // Values returned from our analyzer are passed to the attached listener
                    // We log image analysis results here - you should do something useful
                    // instead!
                    Timber.d( "Average luminosity: $luma")
                })
            }

        // Must unbind the use-cases before rebinding them
        cameraProvider.unbindAll()

        try {
            // A variable number of use-cases can be passed here -
            // camera provides access to CameraControl & CameraInfo
            camera = cameraProvider.bindToLifecycle(
                this, cameraSelector, preview, imageCapture, imageAnalyzer
            )

            // Attach the viewfinder's surface provider to preview use case
            preview?.setSurfaceProvider(viewFinder.createSurfaceProvider())
        } catch (exc: Exception) {
            Timber.e( "Use case binding failed")
        }
    }

    fun getImageCapture(): ImageCapture {
        val metrics = DisplayMetrics().also { viewFinder.display.getRealMetrics(it) }
        Timber.d( "Screen metrics: ${metrics.widthPixels} x ${metrics.heightPixels}")

        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)
        return ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            // We request aspect ratio but no resolution to match preview config, but letting
            // CameraX optimize for whatever specific resolution best fits our use cases
            .setTargetAspectRatio(screenAspectRatio)
            .setFlashMode(flashMode)
            .build()
    }


    /**
     *  [androidx.camera.core.ImageAnalysisConfig] requires enum value of
     *  [androidx.camera.core.AspectRatio]. Currently it has values of 4:3 & 16:9.
     *
     *  Detecting the most suitable ratio for dimensions provided in @params by counting absolute
     *  of preview ratio to one of the provided values.
     *
     *  @param width - preview width
     *  @param height - preview height
     *  @return suitable aspect ratio
     */
    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    /** Returns true if the device has an available back camera. False otherwise */
    private fun hasBackCamera(): Boolean {
        return cameraProvider?.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) ?: false
    }

    /** Returns true if the device has an available front camera. False otherwise */
    private fun hasFrontCamera(): Boolean {
        return cameraProvider?.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA) ?: false
    }

    companion object {

        private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val PHOTO_EXTENSION = ".jpg"
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0

        /** Helper function used to create a timestamped file */
        private fun createFile(baseFolder: File, format: String, extension: String) =
            File(
                baseFolder, SimpleDateFormat(format, Locale.US)
                    .format(System.currentTimeMillis()) + extension
            )
    }
}
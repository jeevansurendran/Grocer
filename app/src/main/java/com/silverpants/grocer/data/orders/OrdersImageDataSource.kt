package com.silverpants.funnycamera.data

import com.silverpants.funnycamera.storage.StorageFiles
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class OrdersImageDataSource @Inject constructor(private val storageFiles: StorageFiles) {

    private fun getOrderDirectory(orderId: String) = "orders/$orderId"

    fun createImageFile(orderId: String) =
        storageFiles.createInternalCacheFile(
            getOrderDirectory(orderId),
            timeStampFileName(orderId)
        )

    fun getAllImageFileNames(orderId: String): Array<String>? =
        storageFiles.getInternalCacheDirectory(getOrderDirectory(orderId)).list()

    fun getAllImageFiles(orderId: String): Array<File>? {
        return storageFiles.getInternalCacheDirectory(getOrderDirectory(orderId)).listFiles()
    }


    fun getTotalImageCount(orderId: String) = getAllImageFileNames(orderId)?.size ?: 0

    companion object {

        private const val TAG = "CameraXBasic"
        private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val PHOTO_EXTENSION = ".jpg"

        private fun timeStampFileName(
            prefix: String,
            format: String = FILENAME,
            extension: String = PHOTO_EXTENSION
        ) =
            prefix + SimpleDateFormat(
                format,
                Locale.getDefault()
            ).format(System.currentTimeMillis()) + extension
    }

}
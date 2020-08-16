package com.silverpants.funnycamera.storage

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class StorageFiles @Inject constructor(@ApplicationContext private val context: Context) {

    fun getInternalCacheDirectory(path: String): File {
        val cacheDir = context.cacheDir.let {
            File(it, path).apply {
                mkdirs()
            }
        }
        return if (cacheDir.exists()) cacheDir else context.filesDir
    }

    fun createInternalCacheFile(
        baseFolder: String,
        file: String
    ): File {
        return File(getInternalCacheDirectory(baseFolder), file)
    }
}
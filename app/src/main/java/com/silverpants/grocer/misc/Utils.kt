package com.silverpants.grocer.misc

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import com.bumptech.glide.Glide
import java.io.File
import java.util.*

/**
 *  The [Utils] object contains all the utility functions of a class.
 *
 *  @author @jeevansurendran
 *  @since 1.0
 */
object Utils {
    fun fromHtml(html: String): CharSequence = HtmlCompat.fromHtml(html, FROM_HTML_MODE_COMPACT)

    fun formatTime(min: Long, sec: Long): String {
        return if (min == 0L) String.format(Locale.ENGLISH, "%02d seconds", sec)
        else String.format(Locale.ENGLISH, "%02d:%02d minutes", min, sec)
    }

    /*
    * Since no disk strategy and all caching is enabled make sure the image file name is unique
    * */
    fun loadImageFromFile(imageView: ImageView, imageFile: File) {
        Glide.with(imageView)
            .load(imageFile)
            .into(imageView)
    }

    fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView)
            .load(imageUrl)
            .into(imageView)
    }

    fun loadImageFromUrlWithPlaceHolder(
        imageView: ImageView,
        imageUrl: String,
        @DrawableRes placeholder: Int
    ) {
        Glide.with(imageView)
            .load(imageUrl)
            .placeholder(placeholder)
            .into(imageView)
    }

    fun loadImageFromFileWithPlaceHolder(
        imageView: ImageView,
        imageFile: File,
        @DrawableRes placeholder: Int
    ) {
        Glide.with(imageView)
            .load(imageFile)
            .placeholder(placeholder)
            .into(imageView)
    }
}
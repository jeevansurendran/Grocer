package com.silverpants.grocer.misc

import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
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
}
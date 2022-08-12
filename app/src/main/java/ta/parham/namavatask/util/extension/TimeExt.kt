package ta.parham.namavatask.util.extension

import android.text.format.DateUtils

/**
 * Convert seconds to time formatted string like 00:00.
 */
fun Int?.toTimeFormat(): String {
    return DateUtils.formatElapsedTime(this?.toLong() ?: 0L)
}
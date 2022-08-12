package ta.parham.namavatask.util.extension

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

private var toast: Toast? = null

/** Shows a toast in the receiving context, cancelling any previous. */
fun Context.toast(message: String) {
    toast?.cancel()
    toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        .apply {
            show()
        }
}

fun Fragment.toast(message: String) {
    requireContext().toast(message)
}

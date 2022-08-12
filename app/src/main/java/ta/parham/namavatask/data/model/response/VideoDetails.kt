package ta.parham.namavatask.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VideoDetails(
    val id: String?,
    val name: String?,
    val description: String?,
    val duration: Int?,
    val width: Int?,
    val height: Int?,
    val thumbnail: String?,
    val commentsCount: Int?,
    val likesCount: Int?,
    val playCount: Long?
) : Parcelable
package ta.parham.namavatask.data.model.response


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("data") val videoData: List<VideoData>,
    val page: Int,
    val paging: Paging,
    @SerializedName("per_page") val perPage: Int,
    val total: Int,
    override val error: String?
) : BaseResponse

data class VideoData(
    val description: String,
    val duration: Int,
    val height: Int,
    val metadata: Metadata,
    val name: String,
    val pictures: Pictures,
    val stats: Stats,
    val uri: String,
    val width: Int
)

data class Metadata(
    val connections: Connections,
    val interactions: Interactions,
    @SerializedName("is_screen_record") val isScreenRecord: Boolean,
    @SerializedName("is_vimeo_create") val isVimeoCreate: Boolean
)

data class Connections(
    val comments: Comments,
    val likes: Likes
)

data class Comments(
    val total: Int
)

data class Likes(
    val total: Int
)

data class Interactions(
    val report: Report
)

data class Report(
    val options: List<String>,
    val reason: List<String>,
    val uri: String
)

data class Pictures(
    @SerializedName("base_link") val baseLink: String,
    val sizes: List<Size>
)

data class Size(
    val height: Int,
    val link: String,
    @SerializedName("link_with_play_button") val linkWithPlayButton: String,
    val width: Int
)

data class Stats(
    val plays: Long?
)

data class Paging(
    val first: String,
    val last: String,
    val next: String,
    val previous: String
)

package ta.parham.namavatask.data.model.response


import com.google.gson.annotations.SerializedName

data class PlayerConfigResponse(
    val request: Request,
    val video: Video,
    @SerializedName("message") override val error: String?
): BaseResponse {
    data class Request(
        val expires: Int,
        val files: Files,
        val timestamp: Int
    ) {
        data class Files(
            val hls: Hls,
            val progressive: List<Progressive>
        ) {
            data class Hls(
                val cdns: Cdns,
                @SerializedName("default_cdn") val defaultCdn: String
            ) {
                data class Cdns(
                    @SerializedName("akfire_interconnect_quic") val akfireInterconnectQuic: UrlString?,
                    @SerializedName("fastly_skyfire") val fastlySkyfire: UrlString?
                ) {
                    data class UrlString(
                        val url: String?
                    )
                }
            }

            data class Progressive(
                val cdn: String,
                val fps: Int,
                val height: Int,
                val id: String,
                val mime: String,
                val profile: String,
                val quality: String,
                val url: String,
                val width: Int
            )
        }
    }

    data class Video(
        val duration: Int,
        val fps: Double,
        val height: Int,
        val id: Long,
        val owner: Owner,
        val title: String,
        val width: Int
    ) {
        data class Owner(
            val id: Long,
            val img: String,
            @SerializedName("img_2x") val img2x: String,
            val name: String,
            val url: String
        )
    }
}
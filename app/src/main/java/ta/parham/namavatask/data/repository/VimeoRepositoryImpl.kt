package ta.parham.namavatask.data.repository

import ta.parham.namavatask.data.model.response.AuthResponse
import ta.parham.namavatask.data.model.response.VideoDetails
import ta.parham.namavatask.data.repository.api.VimeoService
import ta.parham.namavatask.util.network.ApiResponse
import ta.parham.namavatask.util.network.getResponse
import java.io.IOException
import java.net.SocketTimeoutException

class VimeoRepositoryImpl(private val apiService: VimeoService) : VimeoRepository {

    override suspend fun authClient(): ApiResponse<AuthResponse> {
        return try {
            val authResponse = apiService.authenticate().getResponse()

            if (!authResponse.accessToken.isNullOrEmpty())
                ApiResponse.success(authResponse)
            else
                ApiResponse.error(authResponse.error ?: "Can't fetch access token. Please try again later.")
        } catch (timeOutException: SocketTimeoutException) {
            ApiResponse.error("Connection time out. Please check your internet connection.")
        } catch (ioException: IOException) {
            ApiResponse.error("Network connection is unavailable. Please check your internet connection.")
        } catch (exception: Exception) {
            ApiResponse.error("Something went wrong!")
        }
    }

    override suspend fun searchVideo(keyword: String): ApiResponse<List<VideoDetails>> {
        return try {
            val searchResponse = apiService.searchVideo(keyword).getResponse()

            if (searchResponse.error.isNullOrEmpty()) {
                val videosData = searchResponse.videoData.map {
                    VideoDetails(
                        id = it.uri.split("/").last(),
                        name = it.name,
                        description = it.description,
                        duration = it.duration,
                        width = it.width,
                        height = it.height,
                        thumbnail = it.pictures.sizes[it.pictures.sizes.size / 2].link,
                        commentsCount = it.metadata.connections.comments.total,
                        likesCount = it.metadata.connections.likes.total,
                        playCount = it.stats.plays ?: 0L
                    )
                }

                ApiResponse.success(videosData)
            } else
                ApiResponse.error(searchResponse.error)

        } catch (timeOutException: SocketTimeoutException) {
            ApiResponse.error("Connection time out. Please check your internet connection.")
        } catch (ioException: IOException) {
            ApiResponse.error("Network connection is unavailable. Please check your internet connection.")
        } catch (exception: Exception) {
            ApiResponse.error("Something went wrong!")
        }
    }

    override suspend fun playerConfig(videoId: String): ApiResponse<String> {
        return try {
            val response = apiService.playerConfig(videoId).getResponse()

            if (response.error == null) {
                val videoUrl = response.request.files.hls.cdns.akfireInterconnectQuic?.url ?: response.request.files.hls.cdns.fastlySkyfire?.url
                ApiResponse.success(videoUrl!!)
            } else
                ApiResponse.error(response.error)

        } catch (timeOutException: SocketTimeoutException) {
            ApiResponse.error("Connection time out. Please check your internet connection.")
        } catch (ioException: IOException) {
            ApiResponse.error("Network connection is unavailable. Please check your internet connection.")
        } catch (exception: Exception) {
            ApiResponse.error("Something went wrong!")
        }
    }

}
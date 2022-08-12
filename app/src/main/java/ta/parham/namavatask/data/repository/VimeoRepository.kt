package ta.parham.namavatask.data.repository

import ta.parham.namavatask.data.model.response.AuthResponse
import ta.parham.namavatask.data.model.response.VideoDetails
import ta.parham.namavatask.util.network.ApiResponse

interface VimeoRepository {
    /**
     * Get auth token from Vimeo API
     */
    suspend fun authClient(): ApiResponse<AuthResponse>

    /**
     * search videos by query and return list of videos data
     */
    suspend fun searchVideo(keyword: String): ApiResponse<List<VideoDetails>>

    /**
     * get video playable direct link by video
     */
    suspend fun playerConfig(videoId: String): ApiResponse<String>
}
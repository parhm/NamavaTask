package ta.parham.namavatask.data.repository.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ta.parham.namavatask.data.model.response.AuthResponse
import ta.parham.namavatask.data.model.response.PlayerConfigResponse
import ta.parham.namavatask.data.model.response.SearchResponse

interface VimeoService {

    @POST("oauth/authorize/client")
    suspend fun authenticate(): Response<AuthResponse>

    @GET("videos?per_page=15")
    suspend fun searchVideo(@Query("query") keyword: String): Response<SearchResponse>

    @GET("https://player.vimeo.com/video/{id}/config")
    suspend fun playerConfig(@Path("id") videoId: String): Response<PlayerConfigResponse>
}
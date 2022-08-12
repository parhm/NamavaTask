package ta.parham.namavatask.util.network

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import ta.parham.namavatask.data.token.TokenManager
import ta.parham.namavatask.util.Constants

class HttpInterceptor(private val tokenManager: TokenManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val request = original.newBuilder().apply {
            if (tokenManager.getToken().isNullOrEmpty()) {
                val authValue = Credentials.basic(Constants.CLIENT_IDENTIFIER, Constants.CLIENT_SECRET)
                val requestBody = "{\"grant_type\": \"client_credentials\", \"scope\": \"public\"}".toRequestBody("application/json".toMediaTypeOrNull())

                header("Authorization", authValue)
                method(original.method, requestBody)
            } else {
                header("Authorization", "Bearer ${tokenManager.getToken()}")
                method(original.method, original.body)
            }

            addHeader("Accept", "application/vnd.vimeo.*+json;version=3.4")
            addHeader("Content-Type", "application/json")
        }.build()


        return chain.proceed(request)
    }
}
package ta.parham.namavatask.util.network

import retrofit2.Response
import ta.parham.namavatask.util.fromJson

/**
 * Retrofit only gives generic response body when status is Successful.
 * This extension will also parse error body and will give generic response.
 */
inline fun <reified T> Response<T>.getResponse(): T {
    val responseBody = body()
    return if (this.isSuccessful && responseBody != null) {
        responseBody
    } else {
        fromJson(errorBody()!!.string())
    }
}
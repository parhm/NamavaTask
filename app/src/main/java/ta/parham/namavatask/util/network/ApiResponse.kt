package ta.parham.namavatask.util.network

/**
 * Result wrapper class which can have only two possible states i.e. ApiResponse success or failure
 */
sealed class ApiResponse<T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error<T>(val message: String) : ApiResponse<T>()

    companion object {
        fun <T> success(data: T) = Success(data)
        fun <T> error(message: String) = Error<T>(message)
    }

    inline fun onSuccess(block: (T) -> Unit): ApiResponse<T> = apply {
        if (this is Success) {
            block(data)
        }
    }

    inline fun onFailure(block: (String) -> Unit): ApiResponse<T> = apply {
        if (this is Error) {
            block(message)
        }
    }
}

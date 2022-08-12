package ta.parham.namavatask.data.token

/**
 * Manages Token data in persistent storage.
 */
interface TokenManager {
    /**
     * Saves token in persistence storage.
     */
    fun saveToken(token: String?)

    /**
     * Returns the current available token.
     */
    fun getToken(): String?
}
package ta.parham.namavatask.data.token

import android.content.SharedPreferences
import androidx.core.content.edit
import ta.parham.namavatask.util.Constants

class TokenManagerImpl(private val sharedPref: SharedPreferences): TokenManager {
    override fun saveToken(token: String?) {
        sharedPref.edit(commit = true) {
            putString(Constants.ACCESS_TOKEN_KEY, token)
        }
    }

    override fun getToken(): String? = sharedPref.getString(Constants.ACCESS_TOKEN_KEY, null)
}
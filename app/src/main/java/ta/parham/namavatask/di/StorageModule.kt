package ta.parham.namavatask.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.dsl.module
import ta.parham.namavatask.data.token.TokenManager
import ta.parham.namavatask.data.token.TokenManagerImpl
import ta.parham.namavatask.util.Constants

val storageModule = module {
    single { sharedPref(get()) }
    single<TokenManager> { TokenManagerImpl(get()) }
}

fun sharedPref(context: Context): SharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
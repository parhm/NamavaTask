package ta.parham.namavatask.di

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ta.parham.namavatask.BuildConfig
import ta.parham.namavatask.data.repository.api.VimeoService
import ta.parham.namavatask.data.token.TokenManager
import ta.parham.namavatask.util.Constants
import ta.parham.namavatask.util.network.HttpInterceptor
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { okhttpClient(get()) }
    single { retrofitProvider(get()) }
    single { vimeoProvider(get()) }

}

fun OkHttpClient.Builder.setLogger(): OkHttpClient.Builder {
    val logger = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
    }

    return addInterceptor(logger)
}

fun okhttpClient(tokenManager: TokenManager): OkHttpClient = OkHttpClient.Builder()
    .connectTimeout(12, TimeUnit.SECONDS)
    .readTimeout(15, TimeUnit.SECONDS)
    .writeTimeout(15, TimeUnit.SECONDS)
    .addInterceptor(HttpInterceptor(tokenManager))
    .setLogger()
    .build()

fun retrofitProvider(okHttpClient: OkHttpClient): Retrofit {
    val gson = Gson()
        .newBuilder()
        .setLenient()
        .create()

    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
}

fun vimeoProvider(retrofit: Retrofit): VimeoService = retrofit.create(VimeoService::class.java)

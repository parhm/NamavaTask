package ta.parham.namavatask.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

val gson: Gson = Gson().newBuilder().setLenient().create()

inline fun <reified T> fromJson(json: String): T = gson.fromJson(json, object: TypeToken<T>() {}.type)

package edu.mirea.onebeattrue.weatherapp.data.network.api

import edu.mirea.onebeattrue.weatherapp.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.Locale

object ApiFactory {
    private const val BASE_URL = "https://api.weatherapi.com/v1/"
    private const val KEY_PARAM = "key"
    private const val LANG_PARAM = "lang"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val originalRequest = chain.request()

            val newUrl = originalRequest
                .url()
                .newBuilder()
                .addQueryParameter(KEY_PARAM, BuildConfig.API_KEY)
                .addQueryParameter(LANG_PARAM, Locale.getDefault().language)
                .build()

            val newRequest = originalRequest
                .newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val apiService: ApiService = retrofit.create()
}
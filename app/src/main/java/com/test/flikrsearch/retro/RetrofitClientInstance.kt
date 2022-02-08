package com.test.flikrsearch.retro

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientInstance {

    private const val BASE_URL = "https://api.flickr.com/services/rest/"

    private var retrofit: Retrofit? = null

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofitInstance: Retrofit?get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL).client(OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit
        }
}
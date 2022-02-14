package com.test.flikrsearch.retro

import com.test.flikrsearch.pojo.MyResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIServices {

    @GET("?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1")
    suspend fun getSuspendImages(@Query("text") text: String, @Query("page") page: Int) : Response<MyResponse>

    @GET("?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1")
    fun getImages(@Query("text") text: String, @Query("page") page: Int) : Single<MyResponse>

}
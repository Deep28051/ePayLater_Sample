package com.test.flikrsearch

import android.app.Application
import com.test.flikrsearch.retro.APIServices
import com.test.flikrsearch.retro.RetrofitClientInstance

class AppController : Application() {

    companion object {
        lateinit var context: AppController
        lateinit var apiServices: APIServices
    }

    override fun onCreate() {
        super.onCreate()

        context = this;

        apiServices = RetrofitClientInstance.retrofitInstance!!.create(APIServices::class.java)

    }

}
package com.test.flikrsearch

import android.app.Application
import com.test.flikrsearch.dagger.DaggerAPIComponent
import com.test.flikrsearch.dagger.RetroModule
import com.test.flikrsearch.retro.APIServices
import com.test.flikrsearch.retro.RetrofitClientInstance
import javax.inject.Inject

class AppController : Application() {

    lateinit var apiServices:APIServices
        @Inject set

    companion object {
        lateinit var context: AppController
        //lateinit var apiServices: APIServices
    }

    fun getAPI():APIServices{
        return apiServices
    }

    override fun onCreate() {
        super.onCreate()

        context = this

        val component = DaggerAPIComponent.builder().retroModule(RetroModule(RetrofitClientInstance.BASE_URL))
            .build()

        component.inject(this)

        //apiServices = RetrofitClientInstance.retrofitInstance!!.create(APIServices::class.java)

    }



}
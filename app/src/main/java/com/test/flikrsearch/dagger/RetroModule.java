package com.test.flikrsearch.dagger;

import com.google.gson.Gson;
import com.test.flikrsearch.retro.APIServices;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetroModule {

    String BASE;

    public RetroModule(String BASE) {
        this.BASE = BASE;
    }

    @Singleton
    @Provides
    public Gson provideGson() {
        return new Gson();
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttp() {
        return new OkHttpClient();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(Gson gson, OkHttpClient client) {
        return new Retrofit.Builder().
                baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    @Singleton
    @Provides
    public APIServices provideAPIServices(Retrofit retrofit) {
        return retrofit.create(APIServices.class);
    }
}

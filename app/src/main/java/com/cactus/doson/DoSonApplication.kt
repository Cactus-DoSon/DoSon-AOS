package com.cactus.doson

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.cactus.doson.common.AuthenticationInterceptor
import com.cactus.doson.common.Constants
import com.cactus.doson.common.Constants.BASE_URL
import com.cactus.doson.common.Constants.KAKAO_TAG
import com.cactus.doson.common.Constants.PREFERENCES_NAME
import com.cactus.doson.common.util.printLog
import com.cactus.doson.data.RetrofitAPI
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.v2.auth.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class DoSonApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        var keyHash = Utility.getKeyHash(this)
        printLog(KAKAO_TAG, keyHash)


        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        retrofit = provideRetrofit().create(RetrofitAPI::class.java)


        KakaoSdk.init(this, "fa67c50ee7e51cd098f9ead3cf234ec7")
    }


    companion object {
        lateinit var sharedPreferences: SharedPreferences
        lateinit var retrofit: RetrofitAPI
    }

    private fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(buildOkHttpClient())
            .build()


     private fun buildOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addNetworkInterceptor(AuthenticationInterceptor()) // JWT 자동 헤더 전송
            .build()
    }

}
package com.cactus.doson.common

import android.util.Log
import com.cactus.doson.DoSonApplication.Companion.sharedPreferences
import com.cactus.doson.common.Constants.HEADER
import com.cactus.doson.common.Constants.JWT
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class AuthenticationInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val jwtToken: String? = sharedPreferences.getString(JWT, null)
        Log.d("AuthenticationInterceptor $JWT", jwtToken ?: "jwt is null")
        if (jwtToken != null) {
            builder.addHeader(HEADER, jwtToken)
        }
        return chain.proceed(builder.build())
    }
}
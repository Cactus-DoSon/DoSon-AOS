package com.cactus.doson.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cactus.doson.R
import com.cactus.doson.common.Constants.KAKAO_TAG
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginWithKakao()
    }

    private fun loginWithKakao() {


        val callbackForAccount: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                val keyHash: String = Utility.getKeyHash(this)
                Log.e(KAKAO_TAG, "로그인 실패 $keyHash", error)
            } else if (token != null) {
                Log.i(KAKAO_TAG, "로그인 성공 ${token.accessToken}")
            }
        }

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callbackForAccount)
            } else if (token != null) {
                Log.i(KAKAO_TAG, "로그인 성공 ${token.accessToken}")
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }

    }


}

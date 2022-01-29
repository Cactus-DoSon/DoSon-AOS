package com.cactus.doson

import android.app.Application
import com.cactus.doson.common.Constants.KAKAO_TAG
import com.cactus.doson.common.util.printLog
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility

class DoSonApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        var keyHash = Utility.getKeyHash(this)
        printLog(KAKAO_TAG, keyHash)

        KakaoSdk.init(this, "fa67c50ee7e51cd098f9ead3cf234ec7")
    }
}
package com.cactus.doson.data.body

import android.provider.ContactsContract
import com.google.gson.annotations.SerializedName

data class EnterBody(
    @SerializedName("accessCode")
    val accessCode: String,
    @SerializedName("nickname")
    val nickname: String
)

package com.cactus.doson.data.body

import com.google.gson.annotations.SerializedName

data class LoginBody(
    @SerializedName("accessToken")
    val accessToken: String
)

package com.cactus.doson.data.response


import com.google.gson.annotations.SerializedName

data class LoginResult(
    @SerializedName("jwt")
    val jwt: String?
)
package com.cactus.doson.data.response.enter


import com.google.gson.annotations.SerializedName

data class EnterResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("isSuccess")
    val isSuccess: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("result")
    val result: Result?
)
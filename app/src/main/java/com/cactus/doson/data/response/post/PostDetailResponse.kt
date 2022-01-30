package com.cactus.doson.data.response.post


import com.google.gson.annotations.SerializedName

data class PostDetailResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("isSuccess")
    val isSuccess: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("result")
    val result: Result?
)
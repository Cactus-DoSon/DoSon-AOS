package com.cactus.doson.data.response.post


import com.google.gson.annotations.SerializedName

data class AddPostResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("isSuccess")
    val isSuccess: Boolean?,
    @SerializedName("message")
    val message: String?
)
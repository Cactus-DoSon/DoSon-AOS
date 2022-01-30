package com.cactus.doson.data.response.all_list


import com.google.gson.annotations.SerializedName

data class AllListResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("isSuccess")
    val isSuccess: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("result")
    val result: Result?
)
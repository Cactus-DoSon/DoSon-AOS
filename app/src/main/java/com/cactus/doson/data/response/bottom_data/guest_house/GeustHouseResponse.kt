package com.cactus.doson.data.response.bottom_data.guest_house


import com.google.gson.annotations.SerializedName

data class GeustHouseResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("isSuccess")
    val isSuccess: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("result")
    val result: Result?
)
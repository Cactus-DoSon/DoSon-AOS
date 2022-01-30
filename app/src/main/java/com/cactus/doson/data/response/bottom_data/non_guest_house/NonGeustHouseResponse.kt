package com.cactus.doson.data.response.bottom_data.non_guest_house


import com.google.gson.annotations.SerializedName

data class NonGeustHouseResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("isSuccess")
    val isSuccess: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("result")
    val result: Result?
)
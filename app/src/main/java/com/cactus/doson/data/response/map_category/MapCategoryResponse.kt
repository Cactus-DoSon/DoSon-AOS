package com.cactus.doson.data.response.map_category


import com.google.gson.annotations.SerializedName

data class MapCategoryResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("isSuccess")
    val isSuccess: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("result")
    val result: Result?
)
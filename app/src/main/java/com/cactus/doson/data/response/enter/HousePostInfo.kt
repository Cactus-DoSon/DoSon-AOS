package com.cactus.doson.data.response.enter


import com.google.gson.annotations.SerializedName

data class HousePostInfo(
    @SerializedName("postId")
    val postId: Int?,
    @SerializedName("postLatitude")
    val postLatitude: String?,
    @SerializedName("postLongitude")
    val postLongitude: String?
)
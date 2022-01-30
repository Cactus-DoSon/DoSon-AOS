package com.cactus.doson.data.response.bottom_data.non_guest_house


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("category")
    val category: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("create")
    val create: String?,
    @SerializedName("imgUrl")
    val imgUrl: String?,
    @SerializedName("postId")
    val postId: Int?,
    @SerializedName("title")
    val title: String?
)
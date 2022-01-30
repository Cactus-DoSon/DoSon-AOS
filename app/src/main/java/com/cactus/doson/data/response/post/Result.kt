package com.cactus.doson.data.response.post


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("category")
    val category: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("createDate")
    val createDate: String?,
    @SerializedName("imgUrl")
    val imgUrl: String?,
    @SerializedName("likeCnt")
    val likeCnt: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("title")
    val title: String?
)
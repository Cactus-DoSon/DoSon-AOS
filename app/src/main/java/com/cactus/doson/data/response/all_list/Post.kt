package com.cactus.doson.data.response.all_list


import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("content")
    val content: String?,
    @SerializedName("createDate")
    val createDate: String?,
    @SerializedName("imgUrl")
    val imgUrl: String?,
    @SerializedName("postId")
    val postId: Int?,
    @SerializedName("title")
    val title: String?
)
package com.cactus.doson.data.response.all_list


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("postList")
    val postList: List<Post>?
)
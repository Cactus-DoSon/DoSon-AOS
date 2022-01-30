package com.cactus.doson.data.body

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart

data class AddPostBody(
    @SerializedName("file")
    val file: MultipartBody.Part?,
    @SerializedName("houseId")
    val houseId: RequestBody,
    @SerializedName("category")
    val category: RequestBody,
    @SerializedName("title")
    val title: RequestBody,
    @SerializedName("content")
    val content: RequestBody,
    @SerializedName("latitude")
    val latitude: RequestBody,
    @SerializedName("longitude")
    val longitude: RequestBody
)
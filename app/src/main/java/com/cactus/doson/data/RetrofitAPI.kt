package com.cactus.doson.data

import com.cactus.doson.data.body.AddPostBody
import com.cactus.doson.data.body.EnterBody
import com.cactus.doson.data.body.LoginBody
import com.cactus.doson.data.response.LoginResponse
import com.cactus.doson.data.response.enter.EnterResponse
import com.cactus.doson.data.response.map_category.MapCategoryResponse
import com.cactus.doson.data.response.post.AddPostResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface RetrofitAPI {

    @GET("/map")
    fun searchGuestAPI(
        @Query("houseId") houseId: Int,
        @Query("category") category: String
    ): Call<MapCategoryResponse>?


    @POST("/login")
    fun loginApi(
        @Body accessToken: LoginBody
    ): Call<LoginResponse>?

    @POST("/enter")
    fun enterApi(
        @Body body: EnterBody
    ): Call<EnterResponse>?


    @Multipart
    @POST("/post")
    fun addPostApi(
        @PartMap partMap: HashMap<String, RequestBody>, @Part file: MultipartBody.Part
    ): Call<AddPostResponse>?


}
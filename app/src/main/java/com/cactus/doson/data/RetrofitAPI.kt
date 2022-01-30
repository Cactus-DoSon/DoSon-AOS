package com.cactus.doson.data

import com.cactus.doson.data.body.EnterBody
import com.cactus.doson.data.body.LoginBody
import com.cactus.doson.data.response.LoginResponse
import com.cactus.doson.data.response.all_list.AllListResponse
import com.cactus.doson.data.response.bottom_data.guest_house.GeustHouseResponse
import com.cactus.doson.data.response.bottom_data.non_guest_house.NonGeustHouseResponse
import com.cactus.doson.data.response.enter.EnterResponse
import com.cactus.doson.data.response.map_category.MapCategoryResponse
import com.cactus.doson.data.response.post.AddPostResponse
import com.cactus.doson.data.response.post.PostDetailResponse
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


    @GET("/list")
    fun getAllList(
        @Query("houseId") houseId: Int
    ): Call<AllListResponse>?

    @GET("/post/{postId}")
    fun getPostDetail(
        @Path("postId") postId: Int
    ): Call<PostDetailResponse>?


    @GET("/map/{postId}")
    fun getBottomNonGuestHouseData(
        @Path("postId") postId: Int
    ): Call<GeustHouseResponse>?


    @GET("/map/house/{houseId}")
    fun getBottomGuestHouseData(
        @Path("houseId") houseId: Int
    ): Call<NonGeustHouseResponse>?


}
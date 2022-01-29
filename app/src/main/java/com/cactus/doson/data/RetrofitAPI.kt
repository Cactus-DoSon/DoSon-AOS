package com.cactus.doson.data

import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Header


interface RetrofitAPI {

    @GET("map/{houseId}")
    fun searchGuestAPI(@Path("houseId") houseId: Int): Call<Post?>?

    @FormUrlEncoded
    @POST("enter")
    fun guestEnterAPI(@FieldMap param: HashMap<String, String> ): Call<Post?>?
}
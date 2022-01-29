package com.cactus.doson.data


import com.google.gson.annotations.SerializedName

class Post {



    @SerializedName("result")
    private val resultList: Result = Result()
    fun getResultList(): Result? {
        return resultList
    }

    class Result {

        @SerializedName("name")
        val name: String? = null

        @SerializedName("id")
        val id: Int? = null

        @SerializedName("code")
        val code: Int? = null

        @SerializedName("latitude")
        val lat: Double? = null

        @SerializedName("longitude")
        val lon: Double? = null

    }

}
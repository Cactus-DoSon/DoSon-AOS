package com.cactus.doson.data


import com.google.gson.annotations.SerializedName

class Post {

    @SerializedName("isSuccess")
    val success: Boolean? = null

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


        @SerializedName("houseInfo")
        private val houseInfoList: houseInfo = houseInfo()
        fun getHouseInfoList(): houseInfo? {
            return houseInfoList
        }

        @SerializedName("housePostInfo")
        val housePostInfoList : ArrayList<housePost> = ArrayList()
    }


    class housePost{
        @SerializedName("postId")
        var id: Int? = null
        @SerializedName("postLatitude")
        var post_latitude: Int? = null
        @SerializedName("postLongitude")
        var post_longitude: Int? = null

    }


    class houseInfo{
        @SerializedName("houseId")
        var id: Int? = null
        @SerializedName("houseLatitude")
        val lat: Double? = null
        @SerializedName("houseLongitude")
        val lon: Double? = null

    }






}
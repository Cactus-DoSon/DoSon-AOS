package com.cactus.doson.data.response.map_category


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("houseInfo")
    val houseInfo: HouseInfo?,
    @SerializedName("housePostInfo")
    val housePostInfo: List<HousePostInfo>?
)
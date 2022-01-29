package com.cactus.doson.data.response.enter


import com.google.gson.annotations.SerializedName

data class HouseInfo(
    @SerializedName("houseId")
    val houseId: Int?,
    @SerializedName("houseLatitude")
    val houseLatitude: String?,
    @SerializedName("houseLongitude")
    val houseLongitude: String?
)
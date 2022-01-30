package com.cactus.doson.data.response.bottom_data.guest_house


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("address")
    val address: String?,
    @SerializedName("checkin")
    val checkin: String?,
    @SerializedName("checkout")
    val checkout: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("notice")
    val notice: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("rating")
    val rating: Double?
)
package com.cactus.doson.data.body

import com.google.gson.annotations.SerializedName

data class AllListBody(
    @SerializedName("houseId")
    val houseId: Int
)
package com.test.flikrsearch.pojo

import com.google.gson.annotations.SerializedName

class MyResponse {
    @SerializedName("stat")
    val stat: String? = null

    @SerializedName("photos")
    val photos: Photos? = null
    override fun toString(): String {
        return "MyResponse{" +
                "stat = '" + stat + '\'' +
                ",photos = '" + photos + '\'' +
                "}"
    }
}
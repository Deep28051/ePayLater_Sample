package com.test.flikrsearch.pojo

import com.google.gson.annotations.SerializedName

class Photos {
    @SerializedName("perpage")
    val perpage = 0

    @SerializedName("total")
    val total = 0

    @SerializedName("pages")
    val pages = 0

    @SerializedName("photo")
    val photo: List<PhotoItem>? = null

    @SerializedName("page")
    val page = 0
    override fun toString(): String {
        return "Photos{" +
                "perpage = '" + perpage + '\'' +
                ",total = '" + total + '\'' +
                ",pages = '" + pages + '\'' +
                ",photo = '" + photo + '\'' +
                ",page = '" + page + '\'' +
                "}"
    }
}
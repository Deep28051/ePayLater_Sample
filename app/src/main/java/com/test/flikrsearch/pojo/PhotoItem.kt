package com.test.flikrsearch.pojo

import com.google.gson.annotations.SerializedName

class PhotoItem {
    @SerializedName("owner")
    val owner: String? = null

    @SerializedName("server")
    val server: String? = null

    @SerializedName("ispublic")
    val ispublic = 0

    @SerializedName("isfriend")
    val isfriend = 0

    @SerializedName("farm")
    val farm = 0

    @SerializedName("id")
    val id: String? = null

    @SerializedName("secret")
    val secret: String? = null

    @SerializedName("title")
    val title: String? = null

    @SerializedName("isfamily")
    val isfamily = 0
    override fun toString(): String {
        return "PhotoItem{" +
                "owner = '" + owner + '\'' +
                ",server = '" + server + '\'' +
                ",ispublic = '" + ispublic + '\'' +
                ",isfriend = '" + isfriend + '\'' +
                ",farm = '" + farm + '\'' +
                ",id = '" + id + '\'' +
                ",secret = '" + secret + '\'' +
                ",title = '" + title + '\'' +
                ",isfamily = '" + isfamily + '\'' +
                "}"
    }
}
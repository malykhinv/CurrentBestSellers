package com.malykhinv.readdd.model.data

import com.google.gson.annotations.SerializedName

data class Isbns (

    @SerializedName("isbn10")
    var isbn10: String,

    @SerializedName("isbn13")
    var isbn13: String
)
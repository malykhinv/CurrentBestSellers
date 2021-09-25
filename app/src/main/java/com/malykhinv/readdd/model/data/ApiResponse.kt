package com.malykhinv.readdd.model.data

import com.google.gson.annotations.SerializedName

data class ApiResponse (

    val copyright: String,

    @SerializedName("last_modified")
    val lastModified: String,

    @SerializedName("num_results")
    val numResults: Int,

    val results: PublicationDetails,

    val status: String
)
package com.malykhinv.readdd.model.data

import com.google.gson.annotations.SerializedName

data class ApiResponse (

    @SerializedName("status")
    var status: String,

    @SerializedName("copyright")
    var copyright: String,

    @SerializedName("num_results")
    var numberOfResults: String,

    @SerializedName("results")
    var books: PublicationDetails
)
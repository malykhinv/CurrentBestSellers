package com.malykhinv.currentbestsellers.domain.model.books

import com.google.gson.annotations.SerializedName

data class BooksApiResponse (

    val copyright: String,

    @SerializedName("last_modified")
    val lastModified: String,

    @SerializedName("num_results")
    val numResults: Int,

    val results: PublicationDetails,

    val status: String
)
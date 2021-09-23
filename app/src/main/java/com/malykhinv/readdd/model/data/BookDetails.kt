package com.malykhinv.readdd.model.data

import com.google.gson.annotations.SerializedName

data class BookDetails (

    @SerializedName("title")
    var title: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("contributor")
    var contributor: String,

    @SerializedName("author")
    var author: String,

    @SerializedName("contributor_note")
    var contributorNote: String,

    @SerializedName("book_image")
    var coverUrl: String,

    @SerializedName("price")
    var price: Int,

    @SerializedName("age_group")
    var ageGroup: String,

    @SerializedName("publisher")
    var publisher: String,

    @SerializedName("primary_isbn13")
    var primaryIsbn13: String,

    @SerializedName("primary_isbn10")
    var primaryIsbn10: String
)
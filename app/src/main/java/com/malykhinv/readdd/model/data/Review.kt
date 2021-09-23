package com.malykhinv.readdd.model.data

import com.google.gson.annotations.SerializedName

data class Review (

    @SerializedName("book_review_link")
    var bookReviewLink: String,

    @SerializedName("first_chapter_link")
    var firstChapterLink: String,

    @SerializedName("sunday_review_link")
    var sundayReviewLink: String,

    @SerializedName("article_chapter_lin")
    var articleChapterLink: String
)
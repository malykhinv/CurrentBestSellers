package com.malykhinv.currentbestsellers.domain.model.books

import com.google.gson.annotations.SerializedName

data class BookDetails (

    @SerializedName("age_group")
    val ageGroup: String,

    @SerializedName("amazon_product_url")
    val amazonProductUrl: String,

    @SerializedName("article_chapter_link")
    val articleChapterLink: String,

    val asterisk: Int,

    val author: String,

    @SerializedName("book_image")
    val bookImage: String,

    @SerializedName("book_image_height")
    val bookImageHeight: Int,

    @SerializedName("book_image_width")
    val bookImageWidth: Int,

    @SerializedName("book_review_link")
    val bookReviewLink: String,

    @SerializedName("book_uri")
    val bookUri: String,

    @SerializedName("buy_links")
    val buyLinks: List<BuyLink>,

    val contributor: String,

    @SerializedName("contributor_note")
    val contributorNote: String,

    val dagger: Int,

    val description: String,

    @SerializedName("first_chapter_link")
    val firstChapterLink: String,

    val isbns: List<Isbn>,

    val price: String,

    @SerializedName("primary_isbn10")
    val primaryIsbn10: String,

    @SerializedName("primary_isbn13")
    val primaryIsbn13: String,

    val publisher: String,

    val rank: Int,

    @SerializedName("rank_last_week")
    val rankLastWeek: Int,

    @SerializedName("sunday_review_link")
    val sundayReviewLink: String,

    val title: String,

    @SerializedName("weeks_on_list")
    val weeksOnList: Int
)
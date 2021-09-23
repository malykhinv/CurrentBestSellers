package com.malykhinv.readdd.model.data

import com.google.gson.annotations.SerializedName

data class PublicationDetails (

    @SerializedName("list_name")
    var listName: String,

    @SerializedName("display_name")
    var displayName: String,

    @SerializedName("bestsellers_date")
    var bestsellersDate: String,

    @SerializedName("published_date")
    var publishedDate: String,

    @SerializedName("rank")
    var rank: Int,

    @SerializedName("rank_last_week")
    var rankLastWeek: Int,

    @SerializedName("weeks_on_list")
    var weeksOnList: Int,

    @SerializedName("asterisk")
    var asterisk: Int,

    @SerializedName("dagger")
    var dagger: Int,

    @SerializedName("amazon_product_url")
    var amazonProductUrl: String,

    @SerializedName("isbns")
    var isbns: Isbns,

    @SerializedName("book_details")
    var bookDetails: BookDetails,

    @SerializedName("reviews")
    var reviews: Review

)


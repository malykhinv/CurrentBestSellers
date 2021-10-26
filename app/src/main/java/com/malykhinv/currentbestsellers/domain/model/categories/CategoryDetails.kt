package com.malykhinv.currentbestsellers.domain.model.categories

import com.google.gson.annotations.SerializedName

data class CategoryDetails(

    @SerializedName("display_name")
    val displayName: String,

    @SerializedName("list_name")
    val listName: String,

    @SerializedName("list_name_encoded")
    val listNameEncoded: String,

    @SerializedName("newest_published_date")
    val newestPublishedDate: String,

    @SerializedName("oldest_published_date")
    val oldestPublishedDate: String,

    val updated: String
)
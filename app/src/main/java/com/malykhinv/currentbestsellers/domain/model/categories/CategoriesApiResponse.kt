package com.malykhinv.currentbestsellers.domain.model.categories

import com.google.gson.annotations.SerializedName

data class CategoriesApiResponse(

    val copyright: String,

    @SerializedName("num_results")
    val numberOfResults: Int,

    val categories: List<CategoryDetails>,

    val status: String
)
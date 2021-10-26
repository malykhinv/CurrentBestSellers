package com.malykhinv.currentbestsellers.domain.repository

import com.malykhinv.currentbestsellers.domain.model.categories.CategoriesApiResponse
import retrofit2.Call

interface CategoriesRepository {

    fun getCategories(): Call<CategoriesApiResponse>
}
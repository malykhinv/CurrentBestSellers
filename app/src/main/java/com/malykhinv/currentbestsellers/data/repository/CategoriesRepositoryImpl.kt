package com.malykhinv.currentbestsellers.data.repository

import com.malykhinv.currentbestsellers.data.source.RetrofitService
import com.malykhinv.currentbestsellers.domain.model.categories.CategoriesApiResponse
import com.malykhinv.currentbestsellers.domain.repository.CategoriesRepository
import retrofit2.Call

class CategoriesRepositoryImpl (
    private val retrofitService: RetrofitService
    ) : CategoriesRepository {

    override fun getCategories(): Call<CategoriesApiResponse> = retrofitService.getCategories()
}
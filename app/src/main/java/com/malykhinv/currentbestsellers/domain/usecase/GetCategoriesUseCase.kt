package com.malykhinv.currentbestsellers.domain.usecase

import com.malykhinv.currentbestsellers.domain.model.categories.CategoriesApiResponse
import com.malykhinv.currentbestsellers.domain.repository.CategoriesRepository
import retrofit2.Call

class GetCategoriesUseCase constructor(private val repository: CategoriesRepository) {
    fun getCategories() : Call<CategoriesApiResponse> {
        return repository.getCategories()
    }
}
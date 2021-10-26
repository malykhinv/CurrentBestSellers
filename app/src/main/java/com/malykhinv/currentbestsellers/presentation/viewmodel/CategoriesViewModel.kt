package com.malykhinv.currentbestsellers.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.malykhinv.currentbestsellers.domain.model.CategorySorter
import com.malykhinv.currentbestsellers.data.repository.CategoriesRepositoryImpl
import com.malykhinv.currentbestsellers.domain.model.categories.CategoriesApiResponse
import com.malykhinv.currentbestsellers.domain.model.categories.CategoryDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class CategoriesViewModel(private val repositoryImpl: CategoriesRepositoryImpl) : ViewModel() {

    private var categoriesApiResponse: MutableLiveData<CategoriesApiResponse> = MutableLiveData()
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    private var mapOfCategories: Map<String, List<CategoryDetails>> = mapOf()

    fun getCategoriesApiResponse() = categoriesApiResponse
    fun getErrorMessage() = errorMessage
    fun getMapOfCategories() = mapOfCategories

    init {
        viewModelScope.launch {
            downloadCategories()
        }
    }

    private suspend fun downloadCategories() = withContext(Dispatchers.IO) {

        val response = repositoryImpl.getCategories()

        response.enqueue(object: Callback<CategoriesApiResponse> {

            override fun onResponse(
                call: Call<CategoriesApiResponse>,
                response: Response<CategoriesApiResponse>
            ) {
                categoriesApiResponse.postValue(response.body())
                sortCategories()
            }

            override fun onFailure(call: Call<CategoriesApiResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }

    private fun sortCategories() {

        val categories: MutableList<CategoryDetails> = mutableListOf()
        categoriesApiResponse.value?.let { categories.addAll(it.categories) }

        mapOfCategories = CategorySorter().sort(categories)
        Log.d("TAG777", "sortCategories: " + mapOfCategories)
    }
}

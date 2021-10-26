package com.malykhinv.currentbestsellers.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.malykhinv.currentbestsellers.data.repository.BooksRepositoryImpl
import com.malykhinv.currentbestsellers.data.repository.CategoriesRepositoryImpl

class CategoriesViewModelFactory constructor(private val repositoryImpl: CategoriesRepositoryImpl, private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(CategoriesViewModel::class.java) -> {
                CategoriesViewModel(this.repositoryImpl) as T
            }
            else -> {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }

}
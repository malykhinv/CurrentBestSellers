package com.malykhinv.currentbestsellers.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.malykhinv.currentbestsellers.data.repository.BooksRepositoryImpl

class BooksViewModelFactory constructor(private val repositoryImpl: BooksRepositoryImpl, private val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(BooksViewModel::class.java) -> {
                BooksViewModel(this.repositoryImpl, this.application) as T
            }
            else -> {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }

}
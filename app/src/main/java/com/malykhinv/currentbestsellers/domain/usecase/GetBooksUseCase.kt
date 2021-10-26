package com.malykhinv.currentbestsellers.domain.usecase

import com.malykhinv.currentbestsellers.domain.model.books.BooksApiResponse
import com.malykhinv.currentbestsellers.domain.repository.BooksRepository
import retrofit2.Call

class GetBooksUseCase constructor(
    private val repository: BooksRepository,
    private val categoryPath: String?) {

    fun getBooks(): Call<BooksApiResponse> {
        return repository.getBooks(categoryPath)
    }
}
package com.malykhinv.currentbestsellers.data.repository

import com.malykhinv.currentbestsellers.data.source.RetrofitService
import com.malykhinv.currentbestsellers.domain.model.books.BooksApiResponse
import com.malykhinv.currentbestsellers.domain.repository.BooksRepository
import retrofit2.Call

class BooksRepositoryImpl constructor(
    private val retrofitService: RetrofitService, private val categoryPath: String?
    ) : BooksRepository {

    override fun getBooks(categoryPath: String?): Call<BooksApiResponse> = retrofitService.getBooks(categoryPath)
}
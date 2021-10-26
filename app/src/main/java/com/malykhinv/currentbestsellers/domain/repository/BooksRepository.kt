package com.malykhinv.currentbestsellers.domain.repository

import com.malykhinv.currentbestsellers.domain.model.books.BooksApiResponse
import retrofit2.Call

interface BooksRepository {

    fun getBooks(categoryPath: String?): Call<BooksApiResponse>
}
package com.malykhinv.currentbestsellers.model

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getBooks() = retrofitService.getBooks()
}
package com.malykhinv.readdd.model

class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getBooks() = retrofitService.getBooks()
}
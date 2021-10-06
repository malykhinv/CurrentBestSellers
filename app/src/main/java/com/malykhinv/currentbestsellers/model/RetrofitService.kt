package com.malykhinv.currentbestsellers.model

import com.malykhinv.currentbestsellers.model.data.ApiResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET(HARDCOVER_FICTION_PATH + API_REQUEST + API_KEY)
    fun getBooks(): Call<ApiResponse>

    companion object {

        private const val BASE_URL = "https://api.nytimes.com/"
        private const val HARDCOVER_FICTION_PATH = "svc/books/v3/lists/current/hardcover-fiction.json"
        private const val API_REQUEST = "?api-key="
        private const val API_KEY = "XmA5EGgEIQL6AtEAjG1aygzk8AZFKtvN"

        fun create() : RetrofitService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(RetrofitService::class.java)
        }
    }
}
package com.malykhinv.currentbestsellers.data.source

import com.malykhinv.currentbestsellers.domain.model.books.BooksApiResponse
import com.malykhinv.currentbestsellers.domain.model.categories.CategoriesApiResponse
import com.malykhinv.currentbestsellers.utils.Constants.API_KEY
import com.malykhinv.currentbestsellers.utils.Constants.API_REQUEST_FORM
import com.malykhinv.currentbestsellers.utils.Constants.BASE_URL
import com.malykhinv.currentbestsellers.utils.Constants.CATEGORIES
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {
    
    @GET("$BASE_URL $CATEGORIES $API_REQUEST_FORM $API_KEY")
    fun getCategories(): Call<CategoriesApiResponse>

    @GET("{category_path} $API_REQUEST_FORM $API_KEY")
    fun getBooks(@Path("category_path") categoryPath: String?): Call<BooksApiResponse>

    companion object {

        fun create(): RetrofitService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(RetrofitService::class.java)
        }
    }
}
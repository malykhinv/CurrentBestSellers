package com.malykhinv.currentbestsellers.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.drawable.Drawable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.malykhinv.currentbestsellers.model.MainRepository
import com.malykhinv.currentbestsellers.model.data.ApiResponse
import com.malykhinv.currentbestsellers.model.data.BookDetails
import retrofit2.Call
import retrofit2.Response

class MainViewModel(private val repository: MainRepository, private val app: Application) :
    AndroidViewModel(app) {

    private var apiResponse: MutableLiveData<ApiResponse> = MutableLiveData<ApiResponse>()
    private var errorMessage: MutableLiveData<String> = MutableLiveData<String>()
    private var currentBook: MutableLiveData<BookDetails> = MutableLiveData<BookDetails>()
    private var covers: MutableLiveData<Map<Int, Drawable?>> = MutableLiveData<Map<Int, Drawable?>>()
    private var mapOfCovers: MutableMap<Int, Drawable?> = mutableMapOf()

    fun getApiResponse() = apiResponse
    fun getErrorMessage() = errorMessage
    fun getCurrentBook() = currentBook
    fun getCovers() = covers

    init {
        downloadBooks()
    }

    private fun downloadBooks() {

        val response = repository.getBooks()
        response.enqueue(object: retrofit2.Callback<ApiResponse> {

            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                apiResponse.postValue(response.body())
                downloadCovers(response.body())
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    private fun downloadCovers(apiResponse: ApiResponse?) {

        val countOfBooks = apiResponse?.numResults!!.toInt()

        for (i in 0 until countOfBooks) {

            Glide
                .with(app)
                .load(apiResponse?.results?.books?.get(i)?.bookImage)
                .listener(
                @SuppressLint("CheckResult")
                object : RequestListener<Drawable> {

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        mapOfCovers[i] = resource
                        if (mapOfCovers.size == countOfBooks) covers.postValue(mapOfCovers.toMap())
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .preload()
        }
    }

    fun onCardAppeared(position: Int) {
        currentBook.postValue(apiResponse.value?.results?.books?.get(position))
    }
}
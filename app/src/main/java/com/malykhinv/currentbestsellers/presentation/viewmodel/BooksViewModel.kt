package com.malykhinv.currentbestsellers.presentation.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.drawable.Drawable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.malykhinv.currentbestsellers.data.repository.BooksRepositoryImpl
import com.malykhinv.currentbestsellers.domain.model.books.BooksApiResponse
import com.malykhinv.currentbestsellers.domain.model.books.BookDetails
import retrofit2.Call
import retrofit2.Response

class BooksViewModel(private val repositoryImpl: BooksRepositoryImpl, private val app: Application) :
    AndroidViewModel(app) {

    private var booksApiResponse: MutableLiveData<BooksApiResponse> = MutableLiveData()
    private var errorMessage: MutableLiveData<String> = MutableLiveData()
    private var currentBook: MutableLiveData<BookDetails> = MutableLiveData()
    private var covers: MutableLiveData<Map<Int, Drawable?>> = MutableLiveData()
    private var mapOfCovers: MutableMap<Int, Drawable?> = mutableMapOf()

    fun getApiResponse() = booksApiResponse
    fun getErrorMessage() = errorMessage
    fun getCurrentBook() = currentBook
    fun getCovers() = covers

    init {
        downloadBooks()
    }

    private fun downloadBooks() {

        val response = repositoryImpl.getBooks("") // TODO Add path
        response.enqueue(object: retrofit2.Callback<BooksApiResponse> {

            override fun onResponse(
                call: Call<BooksApiResponse>,
                responseBooks: Response<BooksApiResponse>
            ) {
                booksApiResponse.postValue(responseBooks.body())
                downloadCovers(responseBooks.body())
            }

            override fun onFailure(call: Call<BooksApiResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    private fun downloadCovers(booksApiResponse: BooksApiResponse?) {

        booksApiResponse?.numResults?.let {

            for (i in 0 until it) {

                Glide
                    .with(app)
                    .load(booksApiResponse.results.books[i].bookImage)
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
                                if (mapOfCovers.size == it) covers.postValue(mapOfCovers.toMap())
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
    }

    fun onCardAppeared(position: Int) {
        currentBook.postValue(booksApiResponse.value?.results?.books?.get(position))
    }
}
package com.malykhinv.readdd.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.malykhinv.readdd.model.MainRepository
import com.malykhinv.readdd.model.data.ApiResponse
import retrofit2.Call
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository) : ViewModel() {

    var listOfBooks = MutableLiveData<ApiResponse>()
    var errorMessage = MutableLiveData<String>()

    fun getBooks() {

        val response = repository.getBooks()
        response.enqueue(object: retrofit2.Callback<ApiResponse> {

            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                listOfBooks.postValue(response.body())
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

}
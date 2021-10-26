package com.malykhinv.currentbestsellers.di

import com.malykhinv.currentbestsellers.di.modules.NetworkModule
import dagger.Component

@Component (modules = [NetworkModule::class])
interface AppComponent {
    fun getRetrofit()
    fun getGsonConverterFactory()
    fun getCategoriesRepository()
}
package com.malykhinv.currentbestsellers.di.modules

import com.malykhinv.currentbestsellers.data.repository.CategoriesRepositoryImpl
import com.malykhinv.currentbestsellers.data.source.RetrofitService
import com.malykhinv.currentbestsellers.domain.repository.CategoriesRepository
import com.malykhinv.currentbestsellers.utils.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(
        gsonConverterFactory: GsonConverterFactory
    ) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun providesGsonConverterFactory() : GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun providesCategoriesRepository(
        retrofitService: RetrofitService
    ) : CategoriesRepository {
        return CategoriesRepositoryImpl(retrofitService)
    }
}
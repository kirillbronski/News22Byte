package com.bronski.news22byte.di

import com.bronski.news22byte.core.api.NewsApi
import com.bronski.news22byte.core.utils.Constants.Companion.BASE_URL
import com.bronski.news22byte.news.viewmodel.NewsViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    viewModel {
        NewsViewModel(newsRepo = get())
    }

    single { provideNewsApi() }
}

private fun provideNewsApi(): NewsApi {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(NewsApi::class.java)
}
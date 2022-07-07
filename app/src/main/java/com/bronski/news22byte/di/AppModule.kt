package com.bronski.news22byte.di

import com.bronski.news22byte.api.NewsApi
import com.bronski.news22byte.news.NewsViewModel
import com.bronski.news22byte.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    viewModel {
        NewsViewModel(newsRepo = get())
    }

    single { provideInterceptor() }
    single { provideOkHttpClient(loggingInterceptor = get()) }
    single { provideRetrofit(okHttpClient = get()) }
    single { provideNewsApi(retrofit = get()) }
}

private fun provideInterceptor(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)
}

private fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun provideNewsApi(retrofit: Retrofit): NewsApi {
    return retrofit.create(NewsApi::class.java)
}
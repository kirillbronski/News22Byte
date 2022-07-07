package com.bronski.news22byte.di

import com.bronski.news22byte.api.NewsApi
import com.bronski.news22byte.news.INewsRepo
import com.bronski.news22byte.news.NewsRepoImpl
import org.koin.dsl.module

val repoModule = module {
    single { provideINewsRepo(newsApi = get()) }
}

private fun provideINewsRepo(newsApi: NewsApi): INewsRepo {
    return NewsRepoImpl(newsApi)
}
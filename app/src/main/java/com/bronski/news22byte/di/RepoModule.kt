package com.bronski.news22byte.di

import com.bronski.news22byte.core.api.NewsApi
import com.bronski.news22byte.news.model.INewsRepo
import com.bronski.news22byte.news.model.NewsRepoImpl
import org.koin.dsl.module

val repoModule = module {
    single { provideINewsRepo(newsApi = get()) }
}

private fun provideINewsRepo(newsApi: NewsApi): INewsRepo {
    return NewsRepoImpl(newsApi)
}

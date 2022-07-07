package com.bronski.news22byte.news

import com.bronski.news22byte.api.NewsApi
import com.bronski.news22byte.utils.BaseResult

class NewsRepoImpl(
    private val newsApi: NewsApi
) : INewsRepo {

    override suspend fun getNews(): BaseResult =
        runCatching {
            newsApi.getBreakingNews()
        }.fold(
            onSuccess = {
                BaseResult.Success(it)
            },
            onFailure = {
                BaseResult.Error(it.message)
            }
        )
}
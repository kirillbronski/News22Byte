package com.bronski.news22byte.news.model

import com.bronski.news22byte.core.api.NewsApi
import com.bronski.news22byte.core.utils.BaseResult

class NewsRepoImpl(
    private val newsApi: NewsApi,
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
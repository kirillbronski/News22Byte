package com.bronski.news22byte.news.model

import com.bronski.news22byte.core.api.NewsApi
import com.bronski.news22byte.core.utils.BaseResult

class NewsRepoImpl(
    private val newsApi: NewsApi,
) : INewsRepo {

    override suspend fun getNews(page: Int): BaseResult =
        runCatching {
            newsApi.getBreakingNews(pageNumber = page)
        }.fold(
            onSuccess = {
                BaseResult.Success(it)
            },
            onFailure = {
                BaseResult.Error(it.message)
            }
        )
}
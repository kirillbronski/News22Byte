package com.bronski.news22byte.news.model

import com.bronski.news22byte.core.utils.BaseResult

interface INewsRepo {
    suspend fun getNews(): BaseResult
}

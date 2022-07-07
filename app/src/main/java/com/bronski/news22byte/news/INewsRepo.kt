package com.bronski.news22byte.news

import com.bronski.news22byte.utils.BaseResult

interface INewsRepo {
    suspend fun getNews(): BaseResult
}

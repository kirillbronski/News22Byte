package com.bronski.news22byte.utils

import com.bronski.news22byte.api.NewsResponse

sealed class BaseResult {
    class Success(val newsResponse: NewsResponse) : BaseResult()
    class Error(val errorMessage: String?) : BaseResult()
}

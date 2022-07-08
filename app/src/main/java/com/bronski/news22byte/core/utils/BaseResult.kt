package com.bronski.news22byte.core.utils

import com.bronski.news22byte.core.api.NewsResponse

sealed class BaseResult {
    class Success(val newsResponse: NewsResponse) : BaseResult()
    class Error(val errorMessage: String?) : BaseResult()
}

package com.bronski.news22byte.core.api

data class NewsResponse(
    val articles: MutableList<ArticleEntity>,
    val status: String,
    val totalResults: Int,
)
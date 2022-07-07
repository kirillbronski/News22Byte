package com.bronski.news22byte.api

import com.bronski.news22byte.utils.Constants.Companion.API_KEY
import com.bronski.news22byte.utils.Constants.Companion.TOP_HEADLINES
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET(TOP_HEADLINES)
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "ru",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsResponse
}
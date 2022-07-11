package com.bronski.news22byte.news.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bronski.news22byte.core.api.NewsResponse
import com.bronski.news22byte.core.state.ViewState
import com.bronski.news22byte.core.utils.BaseResult
import com.bronski.news22byte.news.model.INewsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel(
    private val newsRepo: INewsRepo,
) : ViewModel() {

    private val _viewState = MutableStateFlow<ViewState>(ViewState.DefaultState)
    val viewState: StateFlow<ViewState?> = _viewState.asStateFlow()

    private var breakingNewsResponse: NewsResponse? = null
    private var page = 1

    fun getPage() : Int {
        return page
    }

    init {
        getNews()
    }

    fun getNews() {
        _viewState.value = ViewState.LoadingState
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.value = newsRepo.getNews(page).run {
                when (this) {
                    is BaseResult.Success -> {
                        page++
                        if(breakingNewsResponse == null) {
                            breakingNewsResponse = this.newsResponse
                        } else {
                            val oldArticles = breakingNewsResponse?.articles
                            val newArticles = this.newsResponse.articles
                            oldArticles?.addAll(newArticles)
                        }
                        ViewState.SuccessState(breakingNewsResponse ?: this.newsResponse)
                    }
                    is BaseResult.Error -> {
                        ViewState.ErrorState(this.errorMessage.toString())
                    }
                }
            }
        }
    }
}
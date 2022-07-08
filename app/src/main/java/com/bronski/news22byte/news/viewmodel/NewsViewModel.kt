package com.bronski.news22byte.news.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        getNews()
    }

    fun getNews() {
        _viewState.value = ViewState.LoadingState
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.value = newsRepo.getNews().run {
                when (this) {
                    is BaseResult.Success -> {
                        ViewState.SuccessState(this.newsResponse)
                    }
                    is BaseResult.Error -> {
                        ViewState.ErrorState(this.errorMessage.toString())
                    }
                }
            }
        }
    }
}
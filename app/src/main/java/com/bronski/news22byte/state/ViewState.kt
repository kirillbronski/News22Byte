package com.bronski.news22byte.state

import com.bronski.news22byte.api.NewsResponse

sealed class ViewState {
    object DefaultState : ViewState()
    object LoadingState : ViewState()
    class SuccessState(val newsResponse: NewsResponse) : ViewState()
    class ErrorState(var message: String?) : ViewState()
}

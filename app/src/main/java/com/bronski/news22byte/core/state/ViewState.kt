package com.bronski.news22byte.core.state

import com.bronski.news22byte.core.api.NewsResponse

sealed class ViewState {
    object DefaultState : ViewState()
    object LoadingState : ViewState()
    class SuccessState(val newsResponse: NewsResponse) : ViewState()
    class ErrorState(var message: String?) : ViewState()
}

package ta.parham.namavatask.ui.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import logcat.logcat
import ta.parham.namavatask.data.repository.VimeoRepository
import ta.parham.namavatask.data.token.TokenManager
import ta.parham.namavatask.ui.base.BaseViewModel
import ta.parham.namavatask.ui.state.HomeState

class HomeViewModel(
    private val vimeoRepository: VimeoRepository,
    private val tokenManager: TokenManager
) : BaseViewModel<HomeState>(initialState = HomeState()) {

    init {
        authClient()
    }

    private fun authClient() {
        if (!tokenManager.getToken().isNullOrEmpty() || state.value.isLoading)
            return

        viewModelScope.launch(Dispatchers.IO) {
            setState { state -> state.copy(isLoading = true) }
            val response = vimeoRepository.authClient()

            response.onSuccess {
                tokenManager.saveToken(it.accessToken)
                setState { state -> state.copy(isLoading = false) }

            }.onFailure {
                setState { state -> state.copy(isLoading = false, error = it) }
            }
        }
    }

    fun search(query: String) {
        if (query.isEmpty() || state.value.isLoading)
            return

        viewModelScope.launch(Dispatchers.IO) {
            if (tokenManager.getToken().isNullOrEmpty()){
                setState { state -> state.copy(isLoading = false, videoData = emptyList(), error = "Access Token is Empty, Trying again to fetch token from server.") }
                authClient()
                return@launch
            }


            setState { state -> state.copy(isLoading = true, videoData = emptyList(), error = null) }
            val response = vimeoRepository.searchVideo(query)

            response.onSuccess {
                setState { state -> state.copy(isLoading = false, videoData = it, error = null) }
            }.onFailure {
                setState { state -> state.copy(isLoading = false, videoData = emptyList(), error = it) }
            }
        }
    }

}
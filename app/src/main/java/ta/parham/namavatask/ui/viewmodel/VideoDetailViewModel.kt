package ta.parham.namavatask.ui.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ta.parham.namavatask.data.repository.VimeoRepository
import ta.parham.namavatask.ui.base.BaseViewModel
import ta.parham.namavatask.ui.state.VideoDetailState

class VideoDetailViewModel(private val vimeoRepository: VimeoRepository) : BaseViewModel<VideoDetailState>(initialState = VideoDetailState()) {

    fun getHlsUrl(videoId: String?) {
        if (videoId.isNullOrEmpty()) {
            setState { state -> state.copy(error = "Can't get player config. Video data not complete. Please Try again.") }
            return
        }

        if (state.value.isLoading)
            return

        viewModelScope.launch(Dispatchers.IO) {
            setState { state -> state.copy(isLoading = true, videoUrl = null, error = null) }
            val videoDetail = vimeoRepository.playerConfig(videoId)

            videoDetail.onSuccess {
                setState { state -> state.copy(isLoading = false, videoUrl = it, error = null) }
            }.onFailure {
                setState { state -> state.copy(isLoading = false, videoUrl = null, error = it) }
            }
        }
    }

    /**
     * Every time phone rotation changed, the last state flow emitted. when user watch video
     * and return to [VideoDetailFragment] previous state emitted and video player activity
     * started again. so empty the video url to solve this problem.
     */
    fun emptyUrlState() {
        setState { state -> state.copy(isLoading = false, videoUrl = null, error = null) }
    }
}
package ta.parham.namavatask.ui.state

import ta.parham.namavatask.data.model.response.VideoDetails

data class HomeState(
    val isLoading: Boolean = false,
    val videoData: List<VideoDetails> = listOf(),
    val error: String? = null
) : State

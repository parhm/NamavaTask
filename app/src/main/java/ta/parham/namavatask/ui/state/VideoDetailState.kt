package ta.parham.namavatask.ui.state

data class VideoDetailState(
    val isLoading: Boolean = false,
    val videoUrl: String? = null,
    val error: String? = null
): State
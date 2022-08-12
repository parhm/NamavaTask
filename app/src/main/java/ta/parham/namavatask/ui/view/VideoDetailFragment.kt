package ta.parham.namavatask.ui.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import logcat.logcat
import org.koin.androidx.viewmodel.ext.android.viewModel
import ta.parham.namavatask.databinding.FragmentVideoDetailBinding
import ta.parham.namavatask.ui.base.BaseFragment
import ta.parham.namavatask.ui.state.VideoDetailState
import ta.parham.namavatask.ui.viewmodel.VideoDetailViewModel
import ta.parham.namavatask.util.extension.loadUrl
import ta.parham.namavatask.util.extension.toTimeFormat
import ta.parham.namavatask.util.extension.toast

class VideoDetailFragment: BaseFragment<FragmentVideoDetailBinding, VideoDetailState, VideoDetailViewModel>() {
    override val viewModel: VideoDetailViewModel by viewModel()
    private val fragmentArgs: VideoDetailFragmentArgs by navArgs()


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentVideoDetailBinding = FragmentVideoDetailBinding.inflate(inflater, container, false)

    override fun initView() {
        val videoDetail = fragmentArgs.videoDetail

        binding.run {
            tvDetailFragmentTitle.text = videoDetail.name
            tvDetailFragmentDescription.text = videoDetail.description
            tvDetailFragmentDuration.text = videoDetail.duration.toTimeFormat()
            tvDetailFragmentComment.text = videoDetail.commentsCount.toString()
            tvDetailFragmentFavorite.text = videoDetail.likesCount.toString()
            tvDetailFragmentViewCount.text = videoDetail.playCount.toString()
            ivDetailFragmentThumbnail.loadUrl(videoDetail.thumbnail)

            ivDetailFragmentPlay.setOnClickListener{ onVideoClick(videoDetail.id.toString()) }
            ivDetailFragmentThumbnail.setOnClickListener{ onVideoClick(videoDetail.id.toString()) }
        }

    }

    override fun render(state: VideoDetailState) {
        logcat { state.toString() }

        state.videoUrl?.let { url ->
            Intent(activity, VideoPlayerActivity::class.java).also { intent ->
                intent.putExtra(VideoPlayerActivity.VIDEO_URL_PARAM, url)
                startActivity(intent)
            }

            viewModel.emptyUrlState()
        }

        state.error?.let {
            toast(it)
        }

        binding.run {
            pbVideoDetailFragment.isVisible = state.isLoading
            ivDetailFragmentPlay.isVisible = !state.isLoading
        }
    }

    private fun onVideoClick(videoUrl: String) {
        viewModel.getHlsUrl(videoUrl)
    }
}
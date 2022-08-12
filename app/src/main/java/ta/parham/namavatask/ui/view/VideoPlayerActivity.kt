package ta.parham.namavatask.ui.view

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import logcat.logcat
import ta.parham.namavatask.databinding.ActivityVideoPlayerBinding
import ta.parham.namavatask.util.extension.hideSystemUI
import ta.parham.namavatask.util.extension.toast
import kotlin.math.max

class VideoPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoPlayerBinding

    private var exoPlayer: ExoPlayer? = null
    private var videoUrl: String? = null
    private var playWhenReady: Boolean = true
    private var startPosition: Long = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState?.let {
            playWhenReady = it.getBoolean(AUTO_PLAY_KEY)
            startPosition = it.getLong(START_POSITION_KEY)
        }

        intent.extras?.also {
            videoUrl = it.getString(VIDEO_URL_PARAM)
        }
    }

    private fun initializePlayer() {
        logcat { "initializePlayer" }

        if(videoUrl.isNullOrEmpty()){
            toast("Can't get Video data. Please try again.")
            finish()
        }

        val mediaDataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(this)

        val mediaSource = HlsMediaSource
            .Factory(mediaDataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoUrl!!))

        val mediaSourceFactory = DefaultMediaSourceFactory(mediaDataSourceFactory)

        exoPlayer = ExoPlayer.Builder(this)
            .setMediaSourceFactory(mediaSourceFactory)
            .build()
            .also {
                it.addMediaSource(mediaSource)
                it.playWhenReady = playWhenReady
                binding.playerViewVideoPlayerActivity.player = it

                if (startPosition != 0L)
                    it.seekTo(startPosition)

                it.prepare()
                it.play()
            }

    }

    private fun releasePlayer() {
        logcat { "Release video player" }

        if(exoPlayer != null) {
            exoPlayer?.release()
            exoPlayer = null
            binding.playerViewVideoPlayerActivity.player = null

        }
    }

    public override fun onStart() {
        super.onStart()

        if (Build.VERSION.SDK_INT > 23) {

            initializePlayer()
            binding.playerViewVideoPlayerActivity.onResume()
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUI()

        if (Build.VERSION.SDK_INT <= 23 || exoPlayer == null) {

            initializePlayer()
            binding.playerViewVideoPlayerActivity.onResume()
        }
    }

    public override fun onPause() {
        super.onPause()

        if (Build.VERSION.SDK_INT <= 23) {

            binding.playerViewVideoPlayerActivity.onPause()
            updatePosition()
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()

        if (Build.VERSION.SDK_INT > 23) {

            binding.playerViewVideoPlayerActivity.onPause()
            updatePosition()
            releasePlayer()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(AUTO_PLAY_KEY, playWhenReady)
        outState.putLong(START_POSITION_KEY, startPosition)
    }

    private fun updatePosition() {
        exoPlayer?.let {
            playWhenReady = it.playWhenReady
            startPosition = max(0, it.contentPosition)
        }
    }

    companion object {
        const val VIDEO_URL_PARAM = "video_url"
        const val START_POSITION_KEY = "position"
        const val AUTO_PLAY_KEY = "auto_play"
    }
}
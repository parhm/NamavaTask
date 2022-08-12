package ta.parham.namavatask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ta.parham.namavatask.data.model.response.VideoDetails
import ta.parham.namavatask.databinding.ItemVideoBinding
import ta.parham.namavatask.util.extension.loadUrl
import ta.parham.namavatask.util.extension.toTimeFormat

class VimeoAdapter(private val onVideoClick: (VideoDetails) -> Unit) : RecyclerView.Adapter<VimeoAdapter.VimeoViewHolder>() {
    private val videosList = mutableListOf<VideoDetails>()

    fun addVideosData(data: List<VideoDetails>) {
        val currentSize = videosList.size
        videosList.clear()
        videosList.addAll(data)

        notifyItemRangeRemoved(0, currentSize);
        notifyItemRangeInserted(0, videosList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VimeoViewHolder {
        return VimeoViewHolder(ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VimeoViewHolder, position: Int) {
        holder.bind(videosList[position], onVideoClick)
    }

    override fun getItemCount(): Int = videosList.size

    inner class VimeoViewHolder(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: VideoDetails, onVideoClick: (VideoDetails) -> Unit) {
            binding.run {
                ivVideoItem.loadUrl(data.thumbnail)
                tvVideoItemName.text = data.name
                tvVideoItemDescription.text = data.description
                tvVideoItemDuration.text = data.duration.toTimeFormat()

                root.setOnClickListener { onVideoClick(data) }
            }
        }
    }
}
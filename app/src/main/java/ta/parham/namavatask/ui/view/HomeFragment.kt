package ta.parham.namavatask.ui.view

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import logcat.logcat
import org.koin.androidx.viewmodel.ext.android.viewModel
import ta.parham.namavatask.data.model.response.VideoDetails
import ta.parham.namavatask.databinding.FragmentHomeBinding
import ta.parham.namavatask.ui.adapter.VimeoAdapter
import ta.parham.namavatask.ui.base.BaseFragment
import ta.parham.namavatask.ui.state.HomeState
import ta.parham.namavatask.ui.viewmodel.HomeViewModel
import ta.parham.namavatask.util.autoCleaned
import ta.parham.namavatask.util.extension.hideKeyboard
import ta.parham.namavatask.util.extension.toast


class HomeFragment : BaseFragment<FragmentHomeBinding, HomeState, HomeViewModel>() {
    private val vimeoAdapter: VimeoAdapter by autoCleaned(initializer = { VimeoAdapter(::onVideoClicked) })
    override val viewModel: HomeViewModel by viewModel()

    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?):
            FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)

    override fun initView() {
        binding.run {
            etHomeFragment.setOnEditorActionListener { _, id, event ->
                return@setOnEditorActionListener if (id == EditorInfo.IME_ACTION_SEARCH || event.action == KeyEvent.ACTION_DOWN) {
                    viewModel.search(etHomeFragment.text.toString())
                    etHomeFragment.hideKeyboard()
                    true
                } else
                    false
            }

            rvHomeFragment.run {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = vimeoAdapter
            }

        }
    }

    override fun render(state: HomeState) {
        logcat { state.toString() }

        binding.run {
            rvHomeFragment.isVisible = state.videoData.isNotEmpty()
            pbHomeFragment.isVisible = state.isLoading

            ivHomeFragmentEmpty.isVisible = state.videoData.isEmpty()
            tvHomeFragmentEmpty.isVisible = state.videoData.isEmpty()
        }

        state.error?.let {
            toast(it)
        }

        if (state.videoData.isNotEmpty())
            vimeoAdapter.addVideosData(state.videoData)
    }

    private fun onVideoClicked(data: VideoDetails) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToVideoDetailFragment(data)
        )
    }

}
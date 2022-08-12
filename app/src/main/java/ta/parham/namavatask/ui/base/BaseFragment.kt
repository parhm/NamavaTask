package ta.parham.namavatask.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ta.parham.namavatask.ui.state.State
import ta.parham.namavatask.util.autoCleaned

/**
 * Base for all fragments in the project
 */
abstract class BaseFragment<VB : ViewBinding, STATE : State, VM : BaseViewModel<STATE>> : Fragment() {

    private var _binding: VB by autoCleaned()
    val binding: VB get() = _binding

    abstract fun initView()
    abstract fun render(state: STATE)
    protected abstract val viewModel: VM
    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeState()
    }

    private fun observeState() {
        viewModel.state
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.CREATED)
            .distinctUntilChanged()
            .onEach { state -> render(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
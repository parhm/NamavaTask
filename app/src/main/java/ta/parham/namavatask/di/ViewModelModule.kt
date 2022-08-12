package ta.parham.namavatask.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ta.parham.namavatask.ui.viewmodel.HomeViewModel
import ta.parham.namavatask.ui.viewmodel.VideoDetailViewModel

val viewModelModule = module {
    viewModel { HomeViewModel(get(), get()) }
    viewModel { VideoDetailViewModel(get()) }

}
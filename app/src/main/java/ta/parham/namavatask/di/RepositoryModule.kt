package ta.parham.namavatask.di

import org.koin.dsl.module
import ta.parham.namavatask.data.repository.VimeoRepository
import ta.parham.namavatask.data.repository.VimeoRepositoryImpl

val repositoryModule = module {
    single<VimeoRepository> { VimeoRepositoryImpl(get()) }
}
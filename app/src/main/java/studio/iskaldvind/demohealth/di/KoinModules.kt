package studio.iskaldvind.demohealth.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import studio.iskaldvind.demohealth.repository.IRepository
import studio.iskaldvind.demohealth.repository.Repository
import studio.iskaldvind.demohealth.viewmodel.MainViewModel

val application = module {
    single<IRepository> { Repository() }
}

val main = module {
    viewModel { MainViewModel(repository = get()) }
}
package academy.learnprogramming.top10downloader.di

import academy.learnprogramming.top10downloader.ui.FeedViewModel
import academy.learnprogramming.top10downloader.viewModels.ViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    abstract fun bindSearchViewModel(feedViewModel: FeedViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}
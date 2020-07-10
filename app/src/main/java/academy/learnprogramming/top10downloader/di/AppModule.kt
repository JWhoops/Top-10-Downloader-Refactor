package academy.learnprogramming.top10downloader.di

import academy.learnprogramming.top10downloader.db.EntryDB
import academy.learnprogramming.top10downloader.db.dao.EntryDao
import academy.learnprogramming.top10downloader.network.FeedAPI
import academy.learnprogramming.top10downloader.util.Constants
import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constants.FEED_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Singleton
    @Provides
    fun provideFeedApi(retrofit: Retrofit): FeedAPI {
        return retrofit.create(FeedAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): EntryDB {
        return Room
                .databaseBuilder(app, EntryDB::class.java, "entry.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: EntryDB): EntryDao {
        return db.entryDao()
    }

}
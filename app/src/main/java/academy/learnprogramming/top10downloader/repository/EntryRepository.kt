package academy.learnprogramming.top10downloader.repository

import academy.learnprogramming.top10downloader.db.dao.EntryDao
import academy.learnprogramming.top10downloader.db.entity.Entry
import academy.learnprogramming.top10downloader.network.FeedAPI
import academy.learnprogramming.top10downloader.util.Constants
import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class EntryRepository @Inject constructor(
        private val entryDao: EntryDao,
        private val feedAPI: FeedAPI
) {
    private val feedData = MediatorLiveData<List<Entry>>()

    val feed: LiveData<List<Entry>>
        get() = feedData


    fun getFeed(type: String, category: String, limit: Int) {
        val cachedSource = entryDao.getFeedBy(type, category, limit)
        feedData.addSource(cachedSource) { cachedEntry ->
            if (cachedEntry.isEmpty()) {
                feedAPI.getTopList(type, category, Constants.LIMIT_HIGH).subscribeOn(Schedulers.io()).subscribe { entries ->
                    val dbEntries: List<Entry> = entries.feed.results.map { entry ->
                        with(entry) {
                            Entry(null, name, artist, release, copyRight, website, type, category)
                        }
                    }
                    entryDao.insert(dbEntries)
                    feedData.postValue(dbEntries.subList(0, limit))
                }
            } else {
                feedData.postValue(cachedEntry)
            }
            feedData.removeSource(cachedSource)
        }
    }

    @SuppressLint("CheckResult")
    fun clearFeed(type: String, category: String, limit: Int) {
        // lol, switch thread
        entryDao.deleteAllEntries().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe { _ ->
            getFeed(type, category, limit)
        }
    }
}
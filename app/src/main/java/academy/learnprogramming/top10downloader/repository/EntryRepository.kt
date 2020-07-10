package academy.learnprogramming.top10downloader.repository

import academy.learnprogramming.top10downloader.db.EntryDB
import academy.learnprogramming.top10downloader.db.dao.EntryDao
import academy.learnprogramming.top10downloader.db.entity.Entry
import academy.learnprogramming.top10downloader.network.FeedAPI
import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton


private const val TAG = "EntryRepository"

@Singleton
class EntryRepository @Inject constructor(
        private val repoDao: EntryDao,
        private val feedAPI: FeedAPI
) {
    val feedData = MediatorLiveData<List<Entry>>()

    fun getFeed(type: String, category: String, limit: Int) {
        val cachedSource = repoDao.getFeedBy(type, category, limit)
        feedData.addSource(cachedSource) { res ->
            if (res.isEmpty()) {
                feedAPI.getTopList(type, category, 25).subscribeOn(Schedulers.io()).subscribe { entries ->
                    val dbEntries: List<Entry> = entries.feed.results.map {
                        with(it) {
                            Entry(null, name, artist, release, copyRight, website, type, category)
                        }
                    }
                    repoDao.insert(dbEntries)
                    Log.d(TAG, " it's empty ${dbEntries.subList(0, limit)}")
                    feedData.postValue(dbEntries.subList(0, limit))
                }
            } else {
                feedData.postValue(res)
                Log.d(TAG, "nah it's not empty $res")
            }
            feedData.removeSource(cachedSource)
        }
    }

    @SuppressLint("CheckResult")
    fun clearFeed(type: String, category: String, limit: Int) {
        // how to asyc better
        repoDao.deleteAllEntries().subscribeOn(Schedulers.io()).subscribe { _ ->
            Log.d(TAG, "it is deleted!!!!!")
        }
        getFeed(type, category, limit)
    }
}
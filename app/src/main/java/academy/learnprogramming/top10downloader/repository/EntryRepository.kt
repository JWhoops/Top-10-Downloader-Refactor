package academy.learnprogramming.top10downloader.repository

import academy.learnprogramming.top10downloader.db.EntryDB
import academy.learnprogramming.top10downloader.db.dao.EntryDao
import academy.learnprogramming.top10downloader.models.FeedResponse
import academy.learnprogramming.top10downloader.network.FeedAPI
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "EntryRepository"

@Singleton
class EntryRepository @Inject constructor(
        private val db: EntryDB,
        private val repoDao: EntryDao,
        private val feedAPI: FeedAPI
) {

    fun getFeed(type: String, category: String, limit: Int): LiveData<FeedResponse> {
        Log.d(TAG, "${repoDao.getAllEntries()}")
        return LiveDataReactiveStreams.fromPublisher(feedAPI.getTopList(type, category, limit)
                                                             .subscribeOn(Schedulers.io())
        )
    }

}
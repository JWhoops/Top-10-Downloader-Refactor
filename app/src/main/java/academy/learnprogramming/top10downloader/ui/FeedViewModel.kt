package academy.learnprogramming.top10downloader.ui

import academy.learnprogramming.top10downloader.DownloadData
import academy.learnprogramming.top10downloader.models.Entry
import academy.learnprogramming.top10downloader.network.FeedAPI
import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject


/**
 * Created by timbuchalka for the Android Oreo using Kotlin course
 * from www.learnprogramming.academy
 */
private const val TAG = "FeedViewModel"

val EMPTY_FEED_LIST: List<FeedEntry> = Collections.emptyList()
val EMPTY_FEED_LIST1: List<Entry> = Collections.emptyList()

class FeedViewModel @Inject constructor(feedAPI: FeedAPI) : ViewModel(), DownloadData.DownloaderCallBack {


    private var downloadData: DownloadData? = null
    private var feedCachedUrl = "INVALIDATED"

    private val feed = MutableLiveData<List<FeedEntry>>()
    private val feed1 = MutableLiveData<List<Entry>>()
    private var feedAPI: FeedAPI? = null

    val feedEntries: LiveData<List<FeedEntry>>
        get() = feed

    val feedEntries1: LiveData<List<Entry>>
        get() = feed1

    init {
        this.feedAPI = feedAPI
        feed.postValue(EMPTY_FEED_LIST)
        feed1.postValue(EMPTY_FEED_LIST1)
    }

    @SuppressLint("CheckResult")
    fun getFeed(type: String, category: String, limit: Int) {
        feedAPI!!.getTopList(type, category, limit)
                .toObservable()
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { feedResponse ->
                            feed1.postValue(feedResponse.feed.results)
                        },
                        { throwable -> Log.d(TAG, "error error error $throwable") }
                )
    }


    fun downloadUrl(feedUrl: String) {
        Log.d(TAG, "downloadUrl: called with url $feedUrl")
        if (feedUrl != feedCachedUrl) {
            Log.d(TAG, "downloadUrl starting AsyncTask")
            downloadData = DownloadData(this)
            downloadData?.execute(feedUrl)
            feedCachedUrl = feedUrl
            Log.d(TAG, "downloadUrl done")
        } else {
            Log.d(TAG, "downloadUrl - URL not changed")
        }
    }

    fun invalidate() {
        feedCachedUrl = "INVALIDATE"
    }

    override fun onDataAvailable(data: List<FeedEntry>) {
        Log.d(TAG, "onDataAvailable called")
        feed.value = data
        Log.d(TAG, "onDataAvailable ends")
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: cancelling pending downloads")
        downloadData?.cancel(true)
    }
}
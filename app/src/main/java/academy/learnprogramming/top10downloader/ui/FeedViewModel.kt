package academy.learnprogramming.top10downloader.ui

import academy.learnprogramming.top10downloader.DownloadData
import academy.learnprogramming.top10downloader.models.Entry
import academy.learnprogramming.top10downloader.models.FeedResponse
import academy.learnprogramming.top10downloader.network.FeedAPI
import academy.learnprogramming.top10downloader.repository.EntryRepository
import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import java.util.*
import javax.inject.Inject


/**
 * Created by timbuchalka for the Android Oreo using Kotlin course
 * from www.learnprogramming.academy
 */
private const val TAG = "FeedViewModel"

val EMPTY_FEED_LIST: List<FeedEntry> = Collections.emptyList()
val EMPTY_FEED_LIST1: List<Entry> = Collections.emptyList()

class FeedViewModel @Inject constructor(var feedAPI: FeedAPI, var entryRepo: EntryRepository) : ViewModel(), DownloadData.DownloaderCallBack {


    private var downloadData: DownloadData? = null
    private var feedCachedUrl = "INVALIDATED"

    private val feed = MutableLiveData<List<FeedEntry>>()
    private val feed1 = MutableLiveData<List<Entry>>()
    private val feed2 = MediatorLiveData<List<Entry>>()

    val feedEntries: LiveData<List<FeedEntry>>
        get() = feed

    val feedEntries1: LiveData<List<Entry>>
        get() = feed1

    val feedEntries2: LiveData<List<Entry>>
        get() = feed2

    init {
        feed.postValue(EMPTY_FEED_LIST)
        feed1.postValue(EMPTY_FEED_LIST1)
        feed2.postValue(EMPTY_FEED_LIST1)
    }

    @SuppressLint("CheckResult")
    fun getFeed(type: String, category: String, limit: Int) {
//        feedAPI.getTopList(type, category, limit)
//                .toObservable()
//                .subscribeOn(Schedulers.io())
//                .subscribe(
//                        { feedResponse ->
//                            feed1.postValue(feedResponse.feed.results)
//                        },
//                        { throwable -> Log.d(TAG, "error error error $throwable") }
//                )

        val source: LiveData<FeedResponse> = entryRepo.getFeed(type, category, limit)
        feed2.addSource(source) { response ->
            Log.d(TAG, "here is the fucking response $response")
            feed2.postValue(response.feed.results)
            feed2.removeSource(source)
        }
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
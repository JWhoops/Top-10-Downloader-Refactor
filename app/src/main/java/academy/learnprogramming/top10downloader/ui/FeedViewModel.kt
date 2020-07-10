package academy.learnprogramming.top10downloader.ui

import academy.learnprogramming.top10downloader.db.entity.Entry
import academy.learnprogramming.top10downloader.repository.EntryRepository
import androidx.lifecycle.*
import java.util.*
import javax.inject.Inject

val EMPTY_FEED_LIST: List<Entry> = Collections.emptyList()

class FeedViewModel @Inject constructor(private val entryRepo: EntryRepository) : ViewModel() {

    private val feed = MediatorLiveData<List<Entry>>()

    val feedEntries: LiveData<List<Entry>>
        get() = feed

    init {
        with(feed) {
            postValue(EMPTY_FEED_LIST)
            addSource(entryRepo.feed) { it ->
                postValue(it)
            }
        }
    }

    fun getFeed(type: String, category: String, limit: Int) {
        entryRepo.getFeed(type, category, limit)
    }

    fun clearFeedBy(type: String, category: String, limit: Int) {
        entryRepo.clearFeed(type, category, limit)
    }

    override fun onCleared() {
        // how to cancel downloading?
        // memory leak
    }
}
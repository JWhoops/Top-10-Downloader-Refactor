package academy.learnprogramming.top10downloader.ui

import academy.learnprogramming.top10downloader.FeedAdapter1
import academy.learnprogramming.top10downloader.R
import academy.learnprogramming.top10downloader.models.Entry
import androidx.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class FeedEntry {
    var name: String = ""
    var artist: String = ""
    var releaseDate: String = ""
    var summary: String = ""
    var imageURL: String = ""

}

private const val TAG = "MainActivity"
private const val STATE_URL = "feedUrl"
private const val STATE_LIMIT = "feedLimit"
private const val STATE_TYPE = "feedType"
private const val STATE_CATEGORY = "feedCategory"

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val feedViewModel: FeedViewModel by viewModels {
        viewModelFactory
    }

    private var feedLimit = 10
    private var feedType = "ios-apps"
    private var feedCategory = "free"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate called")

        val feedAdapter1 = FeedAdapter1(this, R.layout.list_record, EMPTY_FEED_LIST1)
        xmlListView.adapter = feedAdapter1

        if (savedInstanceState != null) {
            feedType = savedInstanceState.getString(STATE_TYPE).toString()
            feedCategory = savedInstanceState.getString(STATE_CATEGORY).toString()
            feedLimit = savedInstanceState.getInt(STATE_LIMIT)
        }

        feedViewModel.getFeed("ios-apps", "free", feedLimit)

//        feedViewModel.feedEntries1.observe(this,
//                                           Observer<List<Entry>> { feedEntries1 -> feedAdapter1.setFeedList(feedEntries1 ?: EMPTY_FEED_LIST1) })

        feedViewModel.feedEntries2.observe(this,
                                           Observer<List<Entry>> { feedEntries2 -> feedAdapter1.setFeedList(feedEntries2 ?: EMPTY_FEED_LIST1) })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.feeds_menu, menu)

        if (feedLimit == 10) {
            menu?.findItem(R.id.mnu10)?.isChecked = true
        } else {
            menu?.findItem(R.id.mnu25)?.isChecked = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnuFree           -> {
                feedType = "ios-apps"
                feedCategory = "free"
            }
            R.id.mnuPaid           -> {
                feedType = "ios-apps"
                feedCategory = "paid"
            }
            R.id.mnuSongs          -> {
                feedType = "apple-music"
                feedCategory = "songs"
            }
            R.id.mnu10, R.id.mnu25 -> {
                if (!item.isChecked) {
                    item.isChecked = true
                    feedLimit = 35 - feedLimit
                }
            }
            R.id.mnuRefresh        -> feedViewModel.invalidate()
            else                   ->
                return super.onOptionsItemSelected(item)
        }
        feedViewModel.getFeed(feedType, feedCategory, feedLimit)
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_TYPE, feedType)
        outState.putString(STATE_CATEGORY, feedCategory)
        outState.putInt(STATE_LIMIT, feedLimit)
    }
}

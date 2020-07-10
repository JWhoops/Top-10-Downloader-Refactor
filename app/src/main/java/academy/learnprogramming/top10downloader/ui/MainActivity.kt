package academy.learnprogramming.top10downloader.ui

import academy.learnprogramming.top10downloader.binding.FeedAdapter
import academy.learnprogramming.top10downloader.R
import academy.learnprogramming.top10downloader.db.entity.Entry
import academy.learnprogramming.top10downloader.util.Constants
import androidx.lifecycle.Observer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


private const val STATE_LIMIT = "feedLimit"
private const val STATE_TYPE = "feedType"
private const val STATE_CATEGORY = "feedCategory"

class MainActivity : DaggerAppCompatActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val feedViewModel: FeedViewModel by viewModels {
        viewModelFactory
    }

    private var feedLimit = Constants.LIMIT_LOW
    private var feedType = Constants.TYPE_APP
    private var feedCategory = Constants.CATEGORY_FREE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val feedAdapter = FeedAdapter(this, R.layout.list_record, EMPTY_FEED_LIST)
        xmlListView.adapter = feedAdapter

        if (savedInstanceState != null) {
            with(savedInstanceState) {
                feedType = getString(STATE_TYPE).toString()
                feedCategory = getString(STATE_CATEGORY).toString()
                feedLimit = getInt(STATE_LIMIT)
            }
        }

        feedViewModel.getFeed(feedType, feedCategory, feedLimit)
        feedViewModel.feedEntries.observe(this,
                                          Observer<List<Entry>> { feedEntries2 ->
                                              feedAdapter.setFeedList(feedEntries2 ?: EMPTY_FEED_LIST)
                                          })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.feeds_menu, menu)

        if (feedLimit == Constants.LIMIT_LOW) {
            menu?.findItem(R.id.mnu10)?.isChecked = true
        } else {
            menu?.findItem(R.id.mnu25)?.isChecked = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var refresh = false
        when (item.itemId) {
            R.id.mnuFree           -> {
                feedType = Constants.TYPE_APP
                feedCategory = Constants.CATEGORY_FREE
            }
            R.id.mnuPaid           -> {
                feedType = Constants.TYPE_APP
                feedCategory = Constants.CATEGORY_PAID
            }
            R.id.mnuSongs          -> {
                feedType = Constants.TYPE_MUSIC
                feedCategory = Constants.CATEGORY_SONGS
            }
            R.id.mnu10, R.id.mnu25 -> {
                if (!item.isChecked) {
                    item.isChecked = true
                    feedLimit = 35 - feedLimit
                }
            }
            R.id.mnuRefresh        -> refresh = true
            else                   ->
                return super.onOptionsItemSelected(item)
        }
        if (refresh) {
            feedViewModel.clearFeedBy(feedType, feedCategory, feedLimit)
        } else {
            feedViewModel.getFeed(feedType, feedCategory, feedLimit)
        }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState) {
            putString(STATE_TYPE, feedType)
            putString(STATE_CATEGORY, feedCategory)
            putInt(STATE_LIMIT, feedLimit)
        }
    }
}

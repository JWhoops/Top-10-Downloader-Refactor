package academy.learnprogramming.top10downloader

import academy.learnprogramming.top10downloader.db.entity.Entry
import academy.learnprogramming.top10downloader.ui.FeedEntry
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

/**
 * Created by timbuchalka for Android O with Kotlin course.
 */

class ViewHolder2(v: View) {
    val tvName: TextView = v.findViewById(R.id.tvName)
    val tvArtist: TextView = v.findViewById(R.id.tvArtist)
    val tvSummary: TextView = v.findViewById(R.id.tvSummary)
}


class FeedAdapter2(context: Context, private val resource: Int, private var applications: List<Entry>)
    : ArrayAdapter<FeedEntry>(context, resource) {

//    private val TAG = "FeedAdapter"
    private val inflater = LayoutInflater.from(context)

    fun setFeedList(feedList: List<Entry>) {
        this.applications = feedList
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        Log.d(TAG, "getView called")
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
//            Log.d(TAG, "getView called with null convertView")
            view = inflater.inflate(resource, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
//            Log.d(TAG, "getView provided a convertView")
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val currentApp = applications[position]

        viewHolder.tvName.text = currentApp.name
        viewHolder.tvArtist.text = currentApp.artist
        viewHolder.tvSummary.text = currentApp.copyRight

        return view
    }

    override fun getCount(): Int {
//        Log.d(TAG, "getCount called")
        return applications.size
    }
}

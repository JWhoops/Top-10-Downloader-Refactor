package academy.learnprogramming.top10downloader.binding

import academy.learnprogramming.top10downloader.R
import academy.learnprogramming.top10downloader.db.entity.Entry
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ViewHolder(v: View) {
    val tvName: TextView = v.findViewById(R.id.tvName)
    val tvArtist: TextView = v.findViewById(R.id.tvArtist)
    val tvSummary: TextView = v.findViewById(R.id.tvSummary)
}

class FeedAdapter(context: Context, private val resource: Int, private var applications: List<Entry>)
    : ArrayAdapter<Entry>(context, resource) {

    private val inflater = LayoutInflater.from(context)

    fun setFeedList(feedList: List<Entry>) {
        this.applications = feedList
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(resource, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
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
        return applications.size
    }
}

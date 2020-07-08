package academy.learnprogramming.top10downloader

import academy.learnprogramming.top10downloader.ui.FeedEntry
import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by timbuchalka for the Android Oreo using Kotlin course
 * from www.learnprogramming.academy
 */
private const val TAG = "DownloadData"

class DownloadData(private val callBack: DownloaderCallBack) : AsyncTask<String, Void, String>() {

    interface DownloaderCallBack {
        fun onDataAvailable(data: List<FeedEntry>)
    }

    override fun onPostExecute(result: String) {

        val parseApplications = ParseApplications()
        if(result.isNotEmpty()) {
            parseApplications.parse(result)
        }

        callBack.onDataAvailable(parseApplications.applications)
    }

    override fun doInBackground(vararg url: String): String {
        Log.d(TAG, "doInBackground: starts with ${url[0]}")
        val rssFeed = downloadXML(url[0])
        if (rssFeed.isEmpty()) {
            Log.e(TAG, "doInBackground: Error downloading")
        }
        return rssFeed
    }

    private fun downloadXML(urlPath: String): String {
        try {
            return URL(urlPath).readText()
        } catch(e: MalformedURLException) {
            Log.d(TAG, "downloadXML: Invalid URL " + e.message)
        } catch(e: IOException) {
            Log.d(TAG, "downloadXML: IO Exception reading data " + e.message)
        } catch(e: SecurityException) {
            Log.d(TAG, "downloadXML: Security exception. Needs permission? " + e.message)
//            e.printStackTrace()
        }

        return ""       // return an empty string if there was an exception
    }
}
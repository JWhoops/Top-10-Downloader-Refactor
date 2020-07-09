package academy.learnprogramming.top10downloader.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class FeedResponse(
        @SerializedName("feed")
        val feed: Feed
)

data class Feed(
        @SerializedName("results")
        val results: List<Entry>
)

data class Entry(
        @SerializedName("name")
        val name: String,
        @SerializedName("artistName")
        val artist: String,
        @SerializedName("releaseDate")
        val release: String,
        @SerializedName("artistUrl")
        val website: String,
        @SerializedName("copyright")
        val copyRight: String
)

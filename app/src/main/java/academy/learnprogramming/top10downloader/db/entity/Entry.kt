package academy.learnprogramming.top10downloader.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry_table")
data class Entry(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        @ColumnInfo(name = "name")
        val name: String,
        @ColumnInfo(name = "artist_name")
        val artist: String,
        @ColumnInfo(name = "release_date")
        val release: String,
        @ColumnInfo(name = "website")
        val website: String,
        @ColumnInfo(name = "copyright")
        val copyRight: String
)
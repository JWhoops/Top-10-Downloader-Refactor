package academy.learnprogramming.top10downloader.db

import academy.learnprogramming.top10downloader.db.dao.EntryDao
import academy.learnprogramming.top10downloader.db.entity.Entry
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Entry::class], version = 1)
abstract class EntryDB : RoomDatabase() {
    abstract fun entryDao(): EntryDao
}
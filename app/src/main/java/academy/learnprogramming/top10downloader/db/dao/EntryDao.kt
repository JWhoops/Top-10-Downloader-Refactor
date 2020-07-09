package academy.learnprogramming.top10downloader.db.dao

import academy.learnprogramming.top10downloader.db.entity.Entry
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface EntryDao {
    @Insert
    fun insert(entries: List<Entry>?)

    @Update
    fun update(entry: Entry?)

    @Delete
    fun delete(entry: Entry?)

    @Delete
    fun deleteEntries(entries: List<Entry>?)

    @Query("DELETE FROM entry_table")
    fun deleteAllEntries()

    @Query("SELECT * FROM entry_table ORDER BY id DESC")
    fun getAllEntries(): LiveData<List<Entry?>?>?
}
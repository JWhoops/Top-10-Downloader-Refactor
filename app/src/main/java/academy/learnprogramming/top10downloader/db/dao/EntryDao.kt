package academy.learnprogramming.top10downloader.db.dao

import academy.learnprogramming.top10downloader.db.entity.Entry
import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Single


@Dao
interface EntryDao {
    @Insert
    fun insert(entries: List<Entry>?)

    @Update
    fun update(entry: Entry?)

    @Delete
    fun delete(entry: Entry?)

    @Delete
    fun deleteEntries(entries: List<Entry>)

    @Query("DELETE FROM entry_table")
    fun deleteAllEntries(): Single<Int>

    @Query("SELECT * FROM entry_table ORDER BY id DESC")
    fun getAllEntries(): LiveData<List<Entry>>

    @Query("SELECT * FROM entry_table WHERE type LIKE :type AND category LIKE :category ORDER BY id ASC LIMIT :limit")
    fun getFeedBy(type:String, category:String, limit: Int): LiveData<List<Entry>>
}
package sk.stuba.fei.i_mobv_projekt.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PubDao {

    @Query("SELECT * from pub_list")
    fun getItems(): LiveData<List<PubModelDatabase>>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: PubModelDatabase)

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(item: List<PubModelDatabase>)

    @Update
    suspend fun update(item: PubModelDatabase)

    @Delete
    suspend fun delete(item: PubModelDatabase)
}
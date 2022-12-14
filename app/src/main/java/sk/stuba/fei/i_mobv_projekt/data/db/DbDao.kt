package sk.stuba.fei.i_mobv_projekt.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import sk.stuba.fei.i_mobv_projekt.data.db.model.BarItem
import sk.stuba.fei.i_mobv_projekt.data.db.model.ContactItem

@Dao
interface DbDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBars(bars: List<BarItem>)

    @Query("DELETE FROM bars")
    suspend fun deleteBars()

    @Query("SELECT * FROM bars order by users DESC, name ASC")
    fun getBarsByCount(): LiveData<List<BarItem>?>

    @Query("SELECT * FROM bars order by name ASC, users DESC")
    fun getBarsByName(): LiveData<List<BarItem>?>
}
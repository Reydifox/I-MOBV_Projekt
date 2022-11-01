package sk.stuba.fei.i_mobv_projekt.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PubModelDatabase::class], version = 2, exportSchema = false)
abstract class PubDatabase : RoomDatabase() {

    abstract fun pubDao(): PubDao

    companion object {
        @Volatile
        private var INSTANCE: PubDatabase? = null

        fun getDatabase(context: Context): PubDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PubDatabase::class.java,
                    "pub_list"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
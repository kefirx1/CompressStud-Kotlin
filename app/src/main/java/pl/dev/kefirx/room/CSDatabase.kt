package pl.dev.kefirx.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Tests::class], version = 2, exportSchema = false)
abstract class CSDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun testsDao(): TestsDao

    companion object{

        private var instance: CSDatabase? = null

        fun getInstance(context: Context) : CSDatabase?{
            if(instance == null){
                instance = Room.databaseBuilder(
                    context,
                    CSDatabase::class.java,
                    "compressStudDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }

        fun deleteInstanceOfDatabase(){
            instance = null
        }

    }

}
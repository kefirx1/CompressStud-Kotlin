package pl.dev.kefirx.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.dev.kefirx.data.Tests
import pl.dev.kefirx.data.User
import pl.dev.kefirx.database.dao.TestsDao
import pl.dev.kefirx.database.dao.UserDao


@Database(entities = [User::class, Tests::class], version = 9, exportSchema = false)
abstract class CSDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun testsDao(): TestsDao

    companion object{

        private var instance: CSDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: getInstance(context).also { instance = it}
        }

        fun getInstance(context: Context) : CSDatabase?{
            if(instance == null){
                instance = Room.databaseBuilder(
                    context,
                    CSDatabase::class.java,
                    "compressStudDB.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }

    }

}
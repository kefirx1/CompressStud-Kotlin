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

        const val DATABASE_NAME = "compressStudDB.db"

    }

}
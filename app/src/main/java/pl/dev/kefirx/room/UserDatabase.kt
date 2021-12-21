package pl.dev.kefirx.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object{

        private var instance: UserDatabase? = null

        fun getInstance(context: Context) : UserDatabase?{
            if(instance == null){
                instance = Room.databaseBuilder(
                    context,
                    UserDatabase::class.java,
                    "userTable")
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
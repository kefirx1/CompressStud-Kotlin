package pl.dev.kefirx.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT COUNT(name) FROM userTable")
    fun getUserCount(): Int

    @Query("SELECT * FROM userTable")
    fun getUserInfo(): User

}
package pl.dev.kefirx.database.dao

import androidx.room.*
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Deferred
import pl.dev.kefirx.data.User

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
    fun getUserInfo(): Observable<User>

    @Query("SELECT * FROM userTable")
    fun getUserInfo2Async(): User

    @Query("DELETE FROM userTable")
    fun deleteAllUserInfo()

}
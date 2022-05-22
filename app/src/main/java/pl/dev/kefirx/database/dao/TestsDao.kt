package pl.dev.kefirx.database.dao

import androidx.room.*
import io.reactivex.rxjava3.core.Observable
import pl.dev.kefirx.data.Tests

@Dao
interface TestsDao {

    @Insert
    fun insert(tests: Tests)

    @Update
    fun update(tests: Tests)

    @Delete
    fun delete(tests: Tests)

    @Query("SELECT * FROM testsTable")
    fun getAllTestsInfo2(): List<Tests>

    @Query("SELECT * FROM testsTable")
    fun getAllTestsInfo(): Observable<List<Tests>>

    @Query("SELECT * FROM testsTable WHERE test_id=:id")
    fun getTestByIdInfo(id: Int): Observable<Tests>

    @Query("DELETE FROM testsTable")
    fun deleteAllTests()

    @Query("SELECT * FROM testsTable ORDER BY dateOfExam LIMIT 3")
    fun getThreeExams(): List<Tests>

    @Query("SELECT * FROM testsTable ORDER BY test_id DESC LIMIT 1")
    fun getNewestExam(): Tests



}
package pl.dev.kefirx.room

import androidx.room.*

@Dao
interface TestsDao {

    @Insert
    fun insert(tests: Tests)

    @Update
    fun update(tests: Tests)

    @Delete
    fun delete(tests: Tests)

    @Query("SELECT * FROM testsTable")
    fun getAllTestsInfo(): List<Tests>

    @Query("SELECT * FROM testsTable WHERE test_id=:id")
    fun getTestByIdInfo(id: Int): Tests




}
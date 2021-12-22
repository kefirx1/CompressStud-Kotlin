package pl.dev.kefirx.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "testsTable")
data class Tests(
    var lesson: String,
    var topic: String,
    var dateOfExam: String,
    var timeOfLearning: Int,
    var watchedVideos: Int,
    var reminder: Int,     //0 - off / 1 - everyday / 2 - every second day / 3 - day before
    var timeOfRemind: String){

    @PrimaryKey(autoGenerate = true)
    var test_id: Int = 0

}
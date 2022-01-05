package pl.dev.kefirx.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "testsTable")
data class Tests(
    var lesson: String,
    var topic: String,
    var dateOfExam: Long,
    var timeOfLearning: Int,
    var watchedVideos: Int,
    var reminder: Int,     //0 - off / 1 - everyday / 2 - every second day / 3 - day before
    var timeOfRemindH: String,
    var timeOfRemindM: String){

    @PrimaryKey(autoGenerate = true)
    var test_id: Int = 0

}
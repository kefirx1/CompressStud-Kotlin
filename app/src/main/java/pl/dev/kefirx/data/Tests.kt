package pl.dev.kefirx.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "testsTable")
data class Tests(
    var lesson: String,
    var topic: String,
    var dateOfExam: Long,
    var timeOfLearning: Double,
    var watchedVideos: Int,
    var reminder: Int,
    var timeOfRemindH: String,
    var timeOfRemindM: String){

    @PrimaryKey(autoGenerate = true)
    var test_id: Int = 0

}
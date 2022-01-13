package pl.dev.kefirx.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "userTable")
data class User(
    var name: String,
    var levelOfEdu: String,
    var likeMusic: Boolean,
    var musicGenres: String,
    var theme: Int){

    @PrimaryKey(autoGenerate = true)
    var user_id: Int = 0
}
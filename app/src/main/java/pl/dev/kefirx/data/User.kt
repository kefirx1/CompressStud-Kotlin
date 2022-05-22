package pl.dev.kefirx.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "userTable")
data class User(
    var name: String,
    var levelOfEdu: String,
    var likeMusic: Boolean,
    var musicGenres: String){

    @PrimaryKey(autoGenerate = true)
    var user_id: Int = 0
}
package pl.dev.kefirx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pl.dev.kefirx.room.Tests

class StudyingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studying)

        val testToDeleteId = intent.getIntExtra("testId", -1)


        println(testToDeleteId)
    }
}
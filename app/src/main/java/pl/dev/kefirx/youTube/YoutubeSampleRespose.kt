package pl.dev.kefirx.youTube

import android.content.Context
import com.google.gson.Gson
import pl.dev.kefirx.MainActivity
import pl.dev.kefirx.json.GetJSONString
import pl.dev.kefirx.json.ytResponse.YoutubeResponseJSON

object YoutubeSampleRespose {

    private val gson = Gson()

    fun getSampleResponse(context: Context): YoutubeResponseJSON {

        val sampleOfYoutubeResponsePath = "sampleOfYoutubeResponse.json"
        val getJsonString = GetJSONString()
        val jsonResponseString = getJsonString.getJsonStringFromAssets(context, sampleOfYoutubeResponsePath)

        return gson.fromJson(jsonResponseString, YoutubeResponseJSON::class.java)

    }

}
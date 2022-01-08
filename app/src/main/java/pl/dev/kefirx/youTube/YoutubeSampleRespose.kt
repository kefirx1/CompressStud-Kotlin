package pl.dev.kefirx.youTube

import android.content.Context
import pl.dev.kefirx.MainActivity
import pl.dev.kefirx.json.GetJSONString
import pl.dev.kefirx.json.YoutubeResponseJSON

object YoutubeSampleRespose {

    fun getSampleResponse(context: Context): YoutubeResponseJSON {

        val sampleOfYoutubeResponsePath = "sampleOfYoutubeResponse.json"
        val getJsonString = GetJSONString()
        val jsonResponseString = getJsonString.getJsonStringFromAssets(context, sampleOfYoutubeResponsePath)

        return MainActivity.gson.fromJson(jsonResponseString, YoutubeResponseJSON::class.java)

    }

}
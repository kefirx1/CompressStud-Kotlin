package pl.dev.kefirx.youTube

import kotlinx.coroutines.Deferred
import pl.dev.kefirx.json.YoutubeResponseJSON
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeAPIService {


    @GET("/youtube/v3/search?maxResults=10")
    fun getResponseAsync(@Query("q") searchKey : String): Deferred<Response<YoutubeResponseJSON>>


}
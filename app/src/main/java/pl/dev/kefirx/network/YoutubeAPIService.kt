package pl.dev.kefirx.network

import pl.dev.kefirx.json.ytResponse.YoutubeResponseJSON
import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.rxjava3.core.Observable

interface YoutubeAPIService {

    @GET("/youtube/v3/search?part=snippet&maxResults=10")
    fun getResponseAsync(@Query("q") searchKey: String): Observable<YoutubeResponseJSON>


}
package pl.dev.kefirx.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import pl.dev.kefirx.MainActivity.Companion.ai
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "https://youtube.googleapis.com"
private val API_KEY = ai.metaData["keyValue"].toString()

class YoutubeRetrofitClient {

    fun getYouTubeService(): YoutubeAPIService = getRetrofit().create(YoutubeAPIService::class.java)

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain -> return@addInterceptor addApiKeyToRequests(chain) }
        .build()

    private fun addApiKeyToRequests(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url()
        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("key", API_KEY).build()
        request.url(newUrl)
        return chain.proceed(request.build())
    }

}
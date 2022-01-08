package pl.dev.kefirx.youTube

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object YoutubeRetrofitClient {

    private const val BASE_URL ="https://youtube.googleapis.com"
    private const val API_KEY = "AIzaSyDxTfhNslMMA7jYCNbXjcJvIVo0dACUNIE"

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor{ chain -> return@addInterceptor addApiKeyToRequests(chain)}
        .build()


    val instance: YoutubeAPIService by lazy{
       val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()

        retrofit.create(YoutubeAPIService::class.java)

    }


    private fun addApiKeyToRequests(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url()
        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("key", API_KEY).build()
        request.url(newUrl)
        return chain.proceed(request.build())
    }

}
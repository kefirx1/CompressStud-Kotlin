package pl.dev.kefirx.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import pl.dev.kefirx.MainActivity
import pl.dev.kefirx.network.YoutubeAPIService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    private const val BASE_URL = "https://youtube.googleapis.com"
    private val API_KEY = MainActivity.ai.metaData["keyValue"].toString()

    private fun addApiKeyToRequests(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val originalHttpUrl = chain.request().url()
        val newUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("key", API_KEY).build()
        request.url(newUrl)
        return chain.proceed(request.build())
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor{chain -> return@addInterceptor addApiKeyToRequests(chain)}
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): YoutubeAPIService =
        retrofit.create(YoutubeAPIService::class.java)


}
package pl.dev.kefirx.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.dev.kefirx.database.CSRepository
import pl.dev.kefirx.database.dao.TestsDao
import pl.dev.kefirx.database.dao.UserDao
import pl.dev.kefirx.network.YoutubeAPIService
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCSDatabase(
        testsDao: TestsDao,
        userDao: UserDao,
        youtubeAPIService: YoutubeAPIService
    ): CSRepository {
        return CSRepository(userDao, testsDao, youtubeAPIService)
    }
}
package pl.dev.kefirx.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.dev.kefirx.database.CSRepository
import pl.dev.kefirx.database.dao.TestsDao
import pl.dev.kefirx.database.dao.UserDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideCSDatabase(
        testsDao: TestsDao,
        userDao: UserDao
    ): CSRepository {
        return CSRepository(userDao, testsDao)
    }
}
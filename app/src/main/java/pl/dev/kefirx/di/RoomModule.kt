package pl.dev.kefirx.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.dev.kefirx.database.CSDatabase
import pl.dev.kefirx.database.dao.TestsDao
import pl.dev.kefirx.database.dao.UserDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideCSDB(@ApplicationContext context: Context): CSDatabase {
        return Room.databaseBuilder(
            context,
            CSDatabase::class.java,
            CSDatabase.DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideTestsDAO(csDatabase: CSDatabase): TestsDao {
        return csDatabase.testsDao()
    }

    @Singleton
    @Provides
    fun provideUserDAO(csDatabase: CSDatabase): UserDao {
        return csDatabase.userDao()
    }

}
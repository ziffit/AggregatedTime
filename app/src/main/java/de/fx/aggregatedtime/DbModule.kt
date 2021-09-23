package de.fx.aggregatedtime

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.fx.aggregatedtime.room.AppDatabase
import de.fx.aggregatedtime.room.ProjectDao
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DbModule {
    @Provides
    fun provideProjectDao(appDatabase: AppDatabase): ProjectDao {
        return appDatabase.projectDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context):AppDatabase{
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "AggregatedTimeDB")
            .allowMainThreadQueries()
            .build()
    }
}
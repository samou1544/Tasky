package com.asma.tasky.di

import android.app.Application
import androidx.room.Room
import com.asma.tasky.feature_management.data.data_source.TaskyDatabase
import com.asma.tasky.feature_management.data.repository.TaskRepositoryImpl
import com.asma.tasky.feature_management.domain.task.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ManagementModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): TaskyDatabase {
        return Room.databaseBuilder(
            app,
            TaskyDatabase::class.java,
            TaskyDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(db: TaskyDatabase): TaskRepository {
        return TaskRepositoryImpl(db.taskyDao)
    }
}
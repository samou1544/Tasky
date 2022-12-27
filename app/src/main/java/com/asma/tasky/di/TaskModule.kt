package com.asma.tasky.di

import com.asma.tasky.feature_management.data.data_source.TaskyDatabase
import com.asma.tasky.feature_management.data.task.remote.TaskApi
import com.asma.tasky.feature_management.data.task.TaskRepositoryImpl
import com.asma.tasky.feature_management.domain.task.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object TaskModule {

    @Provides
    @Singleton
    fun provideTaskApi(client: OkHttpClient): TaskApi {
        return Retrofit.Builder()
            .baseUrl(TaskApi.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(TaskApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(db: TaskyDatabase, api: TaskApi): TaskRepository {
        return TaskRepositoryImpl(db.taskDao, api)
    }
}

package com.asma.tasky.di

import com.asma.tasky.feature_management.data.data_source.TaskyDatabase
import com.asma.tasky.feature_management.data.reminder.ReminderRepositoryImpl
import com.asma.tasky.feature_management.data.reminder.remote.ReminderApi
import com.asma.tasky.feature_management.domain.reminder.repository.ReminderRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReminderModule {

    @Provides
    @Singleton
    fun provideReminderApi(client: OkHttpClient): ReminderApi {
        return Retrofit.Builder()
            .baseUrl(ReminderApi.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ReminderApi::class.java)
    }

    @Provides
    @Singleton
    fun provideReminderRepository(db: TaskyDatabase, api: ReminderApi): ReminderRepository {
        return ReminderRepositoryImpl(db.reminderDao, api)
    }

}

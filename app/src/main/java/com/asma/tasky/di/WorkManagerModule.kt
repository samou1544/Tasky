package com.asma.tasky.di

import android.app.Application
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object WorkManagerModule {

    @Provides
    fun provideWorkManager(app: Application): WorkManager {
        return WorkManager.getInstance(app.applicationContext)
    }
}

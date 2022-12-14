package com.asma.tasky.di

import android.app.Application
import androidx.work.WorkManager
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.asma.tasky.feature_management.data.data_source.TaskyDatabase
import com.asma.tasky.feature_management.data.event.EventRepositoryImpl
import com.asma.tasky.feature_management.data.event.EventUploader
import com.asma.tasky.feature_management.data.event.remote.EventApi
import com.asma.tasky.feature_management.domain.event.repository.EventRepository
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
object EventModule {

    @Provides
    @Singleton
    fun provideImageLoader(app: Application): ImageLoader {
        return ImageLoader.Builder(app)
            .crossfade(true)
            .componentRegistry {
                add(SvgDecoder(app))
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideEventApi(client: OkHttpClient): EventApi {
        return Retrofit.Builder()
            .baseUrl(EventApi.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(EventApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEventUploader(workManager: WorkManager): EventUploader {
        return EventUploader(workManager)
    }

    @Provides
    @Singleton
    fun provideEventRepository(
        api: EventApi,
        app: Application,
        eventUploader: EventUploader,
        db: TaskyDatabase
    ): EventRepository {
        return EventRepositoryImpl(api, app.contentResolver, eventUploader, db.eventDao)
    }
}

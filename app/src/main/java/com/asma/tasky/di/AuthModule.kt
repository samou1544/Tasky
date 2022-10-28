package com.asma.tasky.di

import com.asma.tasky.authentication.data.remote.AuthenticationApi
import com.asma.tasky.authentication.data.repository.AuthenticationRepositoryImpl
import com.asma.tasky.authentication.domain.repository.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthApi(client: OkHttpClient): AuthenticationApi {
        return Retrofit.Builder()
            .baseUrl(AuthenticationApi.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthenticationApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: AuthenticationApi): AuthenticationRepository {
        return AuthenticationRepositoryImpl(api)
    }
}
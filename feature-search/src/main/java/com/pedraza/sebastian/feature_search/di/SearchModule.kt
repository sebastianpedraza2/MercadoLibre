package com.pedraza.sebastian.feature_search.di

import com.pedraza.sebastian.search_data.api.MercadoLibreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Singleton
    @Provides
    fun provideMoviesService(retrofit: Retrofit): MercadoLibreService =
        retrofit.create(MercadoLibreService::class.java)

}
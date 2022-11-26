package com.pedraza.sebastian.feature_search.di

import com.pedraza.sebastian.search_data.api.MercadoLibreService
import com.pedraza.sebastian.search_data.datasource.remote.SearchRemoteDataSource
import com.pedraza.sebastian.search_data.datasource.remote.SearchRemoteDataSourceImpl
import com.pedraza.sebastian.search_data.repository.SearchRepository
import com.pedraza.sebastian.search_data.repository.SearchRepositoryImpl
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
    fun provideMercadoLibreService(retrofit: Retrofit): MercadoLibreService =
        retrofit.create(MercadoLibreService::class.java)

    @Provides
    fun provideSearchRemoteDataSource(mercadoLibreService: MercadoLibreService): SearchRemoteDataSource =
        SearchRemoteDataSourceImpl(mercadoLibreService)

    @Provides
    fun provideSearchRepository(searchRemoteDataSource: SearchRemoteDataSource): SearchRepository =
        SearchRepositoryImpl(searchRemoteDataSource)

}
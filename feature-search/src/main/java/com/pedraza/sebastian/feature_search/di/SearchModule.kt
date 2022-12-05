package com.pedraza.sebastian.feature_search.di

import com.google.gson.Gson
import com.pedraza.sebastian.android_helpers.preferences.StoreHelper
import com.pedraza.sebastian.search_data.api.MercadoLibreService
import com.pedraza.sebastian.search_data.datasource.remote.SearchRemoteDataSource
import com.pedraza.sebastian.search_data.datasource.remote.SearchRemoteDataSourceImpl
import com.pedraza.sebastian.search_domain.repository.SearchRepository
import com.pedraza.sebastian.search_data.repository.SearchRepositoryImpl
import com.pedraza.sebastian.search_domain.usecases.categories.GetCategoriesUseCase
import com.pedraza.sebastian.search_domain.usecases.categories.GetCategoriesUseCaseImpl
import com.pedraza.sebastian.search_domain.usecases.items.*
import com.pedraza.sebastian.search_domain.usecases.suggestions.GetSearchSuggestionsUseCase
import com.pedraza.sebastian.search_domain.usecases.suggestions.GetSearchSuggestionsUseCaseImpl
import com.pedraza.sebastian.search_domain.usecases.suggestions.SaveSearchSuggestionUseCase
import com.pedraza.sebastian.search_domain.usecases.suggestions.SaveSearchSuggestionUseCaseImpl
import com.pedraza.sebastian.search_presentation.utils.PaginationFactory
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

    @Provides
    fun provideGetCategoriesUseCase(searchRepository: SearchRepository): GetCategoriesUseCase =
        GetCategoriesUseCaseImpl(searchRepository)

    @Provides
    fun provideGetItemDetailUseCase(searchRepository: SearchRepository): GetItemDetailUseCase =
        GetItemDetailUseCaseImpl(searchRepository)

    @Provides
    fun provideGetItemsByCategoryUseCase(searchRepository: SearchRepository): GetItemsByCategoryUseCase =
        GetItemsByCategoryUseCaseImpl(searchRepository)

    @Provides
    fun provideSearchItemsUseCase(searchRepository: SearchRepository): SearchItemsUseCase =
        SearchItemsUseCaseImpl(searchRepository)

    @Provides
    fun provideGetSearchSuggestionsUseCase(
        storeHelper: StoreHelper,
        gson: Gson
    ): GetSearchSuggestionsUseCase = GetSearchSuggestionsUseCaseImpl(storeHelper, gson)

    @Provides
    fun providesSaveSearchSuggestionUseCase(
        storeHelper: StoreHelper,
        gson: Gson
    ): SaveSearchSuggestionUseCase = SaveSearchSuggestionUseCaseImpl(storeHelper, gson)

    @Provides
    fun providePaginationFactory(): PaginationFactory = PaginationFactory()
}

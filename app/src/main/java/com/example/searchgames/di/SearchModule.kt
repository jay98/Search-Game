package com.example.searchgames.di

import com.example.searchgames.ui.search.SearchResultTransformer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Singleton

@InstallIn(FragmentComponent::class)
@Module
object SearchModule {

    @Provides
    @Singleton
    fun provideSearchResultTransformer() = SearchResultTransformer()
}
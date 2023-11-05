package com.betulgules.capstoneproject.di

import com.betulgules.capstoneproject.data.repository.ProductRepository
import com.betulgules.capstoneproject.data.source.remote.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductRepository(productService: ProductService) = ProductRepository(productService)
}
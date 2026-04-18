package com.andresdevs.restaurant.di_DependencyInjection

import com.andresdevs.restaurant.data.firebase.ProductoFirebaseService
import com.andresdevs.restaurant.data.local.dao.ProductCacheDao
import com.andresdevs.restaurant.data.repository.ProductoRepositoryImpl
import com.andresdevs.restaurant.domain.repository.ProductoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductoRepositoryModule {

    @Provides
    @Singleton
    fun provideProductoRepository(
        service: ProductoFirebaseService,
        cacheDao: ProductCacheDao
    ): ProductoRepository {
        return ProductoRepositoryImpl(service, cacheDao)
    }
}

package com.andresdevs.restaurant.di_DependencyInjection

import com.andresdevs.restaurant.data.firebase.CategoriaFirebaseService
import com.andresdevs.restaurant.data.repository.CategoriaRepositoryImpl
import com.andresdevs.restaurant.domain.repository.CategoriaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoriaRepositoryModule {

    @Provides
    @Singleton
    fun provideCategoriaRepository(
        service: CategoriaFirebaseService
    ): CategoriaRepository {
        return CategoriaRepositoryImpl(service)
    }
}

package com.andresdevs.restaurant.di_DependencyInjection

import com.andresdevs.restaurant.data.firebase.UsuarioFirebaseService
import com.andresdevs.restaurant.data.repository.UsuarioRepositoryImpl
import com.andresdevs.restaurant.domain.repository.UsuarioRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UsuarioRepositoryModule {

    @Provides
    @Singleton
    fun provideUsuarioRepository(
        service: UsuarioFirebaseService
    ): UsuarioRepository {
        return UsuarioRepositoryImpl(service)
    }
}

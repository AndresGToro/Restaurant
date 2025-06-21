package com.andresdevs.restaurant.di_DependencyInjection

import com.andresdevs.restaurant.domain.repository.UsuarioRepository
import com.andresdevs.restaurant.domain.usecase.usuario.CreateUsuarioUseCase
import com.andresdevs.restaurant.domain.usecase.usuario.DeleteUsuarioUseCase
import com.andresdevs.restaurant.domain.usecase.usuario.GetUsuarioUseCase
import com.andresdevs.restaurant.domain.usecase.usuario.UpdateUsuarioUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UsuarioUseCaseModule {

    @Provides
    fun provideGetUsuariosUseCase(
        repository: UsuarioRepository
    ): GetUsuarioUseCase = GetUsuarioUseCase(repository)

    @Provides
    fun provideCreateUsuarioUseCase(
        repository: UsuarioRepository
    ): CreateUsuarioUseCase = CreateUsuarioUseCase(repository)

    @Provides
    fun provideUpdateUsuarioUseCase(
        repository: UsuarioRepository
    ): UpdateUsuarioUseCase = UpdateUsuarioUseCase(repository)

    @Provides
    fun provideDeleteUsuarioUseCase(
        repository: UsuarioRepository
    ): DeleteUsuarioUseCase = DeleteUsuarioUseCase(repository)
}

package com.andresdevs.restaurant.di_DependencyInjection

import com.andresdevs.restaurant.domain.repository.CategoriaRepository
import com.andresdevs.restaurant.domain.usecase.categoria.CreateCategoriaUseCase
import com.andresdevs.restaurant.domain.usecase.categoria.DeleteCategoriaUseCase
import com.andresdevs.restaurant.domain.usecase.categoria.GetCategoriaUseCase
import com.andresdevs.restaurant.domain.usecase.categoria.UpdateCategoriaUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object CategoriaUseCaseModule {

    @Provides
    fun provideGetCategoriasUseCase(
        repository: CategoriaRepository
    ): GetCategoriaUseCase = GetCategoriaUseCase(repository)

    @Provides
    fun provideCreateCategoriaUseCase(
        repository: CategoriaRepository
    ): CreateCategoriaUseCase = CreateCategoriaUseCase(repository)

    @Provides
    fun provideUpdateCategoriaUseCase(
        repository: CategoriaRepository
    ): UpdateCategoriaUseCase = UpdateCategoriaUseCase(repository)

    @Provides
    fun provideDeleteCategoriaUseCase(
        repository: CategoriaRepository
    ): DeleteCategoriaUseCase = DeleteCategoriaUseCase(repository)
}

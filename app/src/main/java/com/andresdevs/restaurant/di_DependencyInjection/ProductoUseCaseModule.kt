package com.andresdevs.restaurant.di_DependencyInjection

import com.andresdevs.restaurant.domain.repository.ProductoRepository
import com.andresdevs.restaurant.domain.usecase.producto.CreateProductoUseCase
import com.andresdevs.restaurant.domain.usecase.producto.DeleteProductoUseCase
import com.andresdevs.restaurant.domain.usecase.producto.GetProductoUseCase
import com.andresdevs.restaurant.domain.usecase.producto.UpdateProductoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ProductoUseCaseModule {

    @Provides
    fun provideGetProductosUseCase(
        repository: ProductoRepository
    ): GetProductoUseCase = GetProductoUseCase(repository)

    @Provides
    fun provideCreateProductoUseCase(
        repository: ProductoRepository
    ): CreateProductoUseCase = CreateProductoUseCase(repository)

    @Provides
    fun provideUpdateProductoUseCase(
        repository: ProductoRepository
    ): UpdateProductoUseCase = UpdateProductoUseCase(repository)

    @Provides
    fun provideDeleteProductoUseCase(
        repository: ProductoRepository
    ): DeleteProductoUseCase = DeleteProductoUseCase(repository)
}

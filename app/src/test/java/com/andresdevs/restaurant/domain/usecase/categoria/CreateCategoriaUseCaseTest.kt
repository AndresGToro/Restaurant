package com.andresdevs.restaurant.domain.usecase.categoria

import com.andresdevs.restaurant.domain.model.Categoria
import com.andresdevs.restaurant.domain.repository.CategoriaRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CreateCategoriaUseCaseTest {

    @Test
    fun `invoke should persist categoria in repository`() = runTest {
        val fakeRepository = FakeCategoriaRepository()
        val useCase = CreateCategoriaUseCase(fakeRepository)
        val categoria = Categoria(
            codeCategoria = "CAT-1",
            nombre = "Bebidas",
            imagenUrl = "https://img.dev/bebidas.jpg",
            estado = "Activo"
        )

        val success = useCase(categoria)

        assertTrue(success)
        assertEquals(1, fakeRepository.items.size)
        assertEquals("Bebidas", fakeRepository.items.first().nombre)
    }

    private class FakeCategoriaRepository : CategoriaRepository {
        val items = mutableListOf<Categoria>()

        override suspend fun getCategorias(): List<Categoria> = items

        override suspend fun createCategoria(categoria: Categoria): Boolean {
            items.add(categoria)
            return true
        }

        override suspend fun updateCategoria(categoria: Categoria): Boolean {
            return true
        }

        override suspend fun deleteCategoria(id: String): Boolean {
            return true
        }
    }
}

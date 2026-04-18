package com.andresdevs.restaurant.presentation.usuario

import com.andresdevs.restaurant.domain.model.Usuario
import com.andresdevs.restaurant.domain.repository.UsuarioRepository
import com.andresdevs.restaurant.domain.usecase.usuario.CreateUsuarioUseCase
import com.andresdevs.restaurant.domain.usecase.usuario.DeleteUsuarioUseCase
import com.andresdevs.restaurant.domain.usecase.usuario.GetUsuarioUseCase
import com.andresdevs.restaurant.domain.usecase.usuario.UpdateUsuarioUseCase
import com.andresdevs.restaurant.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UsuarioViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `guardarUsuario should emit validation error when required fields are missing`() = runTest {
        val fakeRepository = FakeUsuarioRepository()
        val viewModel = buildViewModel(fakeRepository)

        viewModel.guardarUsuario()
        advanceUntilIdle()

        assertEquals("Cedula, nombre y usuario son obligatorios", viewModel.state.value.error)
    }

    @Test
    fun `guardarUsuario should persist and clear form on success`() = runTest {
        val fakeRepository = FakeUsuarioRepository()
        val viewModel = buildViewModel(fakeRepository)
        viewModel.onCedulaChange("123")
        viewModel.onNombreCompletoChange("Andres Garces")
        viewModel.onUsuarioChange("andres")
        viewModel.onCargoChange("Admin")
        viewModel.onCelularChange("3001112233")
        viewModel.onCorreoChange("andres@test.com")
        viewModel.onContrasenaChange("123456")
        viewModel.onDireccionChange("Calle 123")

        viewModel.guardarUsuario()
        advanceUntilIdle()

        assertTrue(viewModel.state.value.isSuccess)
        assertNull(viewModel.state.value.error)
        assertTrue(fakeRepository.items.any { it.cedula == "123" })
        assertEquals("", viewModel.state.value.nombreCompleto)
    }

    private fun buildViewModel(repository: UsuarioRepository): UsuarioViewModel {
        return UsuarioViewModel(
            getUsuarioUseCase = GetUsuarioUseCase(repository),
            createUsuarioUseCase = CreateUsuarioUseCase(repository),
            updateUsuarioUseCase = UpdateUsuarioUseCase(repository),
            deleteUsuarioUseCase = DeleteUsuarioUseCase(repository)
        )
    }

    private class FakeUsuarioRepository : UsuarioRepository {
        val items = mutableListOf<Usuario>()

        override suspend fun getUsuarios(): List<Usuario> = items

        override suspend fun createUsuario(usuario: Usuario): Boolean {
            items.add(usuario)
            return true
        }

        override suspend fun updateUsuario(usuario: Usuario): Boolean {
            val index = items.indexOfFirst { it.cedula == usuario.cedula }
            if (index >= 0) {
                items[index] = usuario
                return true
            }
            return false
        }

        override suspend fun deleteUsuario(id: String): Boolean {
            return items.removeIf { it.cedula == id }
        }
    }
}

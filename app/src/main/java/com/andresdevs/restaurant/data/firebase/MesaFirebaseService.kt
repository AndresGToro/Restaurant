package com.andresdevs.restaurant.data.firebase

import com.andresdevs.restaurant.data.model.MesaDto
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class MesaFirebaseService {
    private val database = FirebaseDatabase.getInstance().getReference("Mesas")

    suspend fun getMesas(): List<MesaDto> {
        return try {
            val snapshot = database.get().await()
            snapshot.children.mapNotNull { it.getValue(MesaDto::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun updateMesa(mesa: MesaDto): Boolean {
        return try {
            database.child(mesa.id).setValue(mesa).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}

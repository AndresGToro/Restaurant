package com.andresdevs.restaurant.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andresdevs.restaurant.data.local.entity.ProductCacheEntity

@Dao
interface ProductCacheDao {
    @Query("SELECT * FROM products_cache")
    suspend fun getAll(): List<ProductCacheEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<ProductCacheEntity>)

    @Query("DELETE FROM products_cache WHERE codeProducto = :id")
    suspend fun deleteById(id: String)
}

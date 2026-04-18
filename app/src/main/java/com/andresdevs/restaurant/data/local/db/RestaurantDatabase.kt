package com.andresdevs.restaurant.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andresdevs.restaurant.data.local.dao.ProductCacheDao
import com.andresdevs.restaurant.data.local.entity.ProductCacheEntity

@Database(
    entities = [ProductCacheEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RestaurantDatabase : RoomDatabase() {
    abstract fun productCacheDao(): ProductCacheDao
}

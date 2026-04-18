package com.andresdevs.restaurant.di_DependencyInjection

import android.content.Context
import androidx.room.Room
import com.andresdevs.restaurant.data.local.dao.ProductCacheDao
import com.andresdevs.restaurant.data.local.db.RestaurantDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): RestaurantDatabase {
        return Room.databaseBuilder(
            context,
            RestaurantDatabase::class.java,
            "restaurant_local.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductCacheDao(db: RestaurantDatabase): ProductCacheDao {
        return db.productCacheDao()
    }
}

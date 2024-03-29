package com.furkanmulayim.tarifce.di

import android.content.Context
import androidx.room.Room
import com.furkanmulayim.tarifce.data.db.food.FoodDao
import com.furkanmulayim.tarifce.data.db.food.FoodDatabase
import com.furkanmulayim.tarifce.data.db.saved_food.SavedDao
import com.furkanmulayim.tarifce.data.db.saved_food.SavedDatabase
import com.furkanmulayim.tarifce.data.db.shoping_list.ShoppingListDao
import com.furkanmulayim.tarifce.data.db.shoping_list.ShoppingListDatabase
import com.furkanmulayim.tarifce.data.service.food.FoodAPIService
import com.furkanmulayim.tarifce.repository.FoodDaoRepository
import com.furkanmulayim.tarifce.repository.FoodRepository
import com.furkanmulayim.tarifce.repository.SavedDaoRepository
import com.furkanmulayim.tarifce.repository.ShopListDaoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @Provides
    @Singleton
    fun provideSavedDaoRepository(sdao: SavedDao): SavedDaoRepository {
        return SavedDaoRepository(sdao)
    }
    @Provides
    @Singleton
    fun provideSavedDao(@ApplicationContext context: Context): SavedDao {
        val vt = Room.databaseBuilder(context, SavedDatabase::class.java, name = "saved.sqlite")
            .createFromAsset("saved.sqlite").build()
        return vt.shopListDao()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Provides
    @Singleton
    fun provideShopDaoRepository(fdao: ShoppingListDao): ShopListDaoRepository {
        return ShopListDaoRepository(fdao)
    }
    @Provides
    @Singleton
    fun provideShopsDao(@ApplicationContext context: Context): ShoppingListDao {
        val vt = Room.databaseBuilder(context, ShoppingListDatabase::class.java, name = "shopliste.sqlite")
            .createFromAsset("shopliste.sqlite").build()
        return vt.shopListDao()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Provides
    @Singleton
    fun provideCardsDaoRepository(fdao: FoodDao): FoodDaoRepository {
        return FoodDaoRepository(fdao)
    }
    @Provides
    @Singleton
    fun provideCardsDao(@ApplicationContext context: Context): FoodDao {
        val vt = Room.databaseBuilder(context, FoodDatabase::class.java, name = "foods.sqlite")
            .createFromAsset("foods.sqlite").build()
        return vt.foodDao()
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
}
package com.example.shoppinglist.di

import android.app.Application
import com.example.shoppinglist.data.AppDatabase
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.data.ShoppingListDao
import com.example.shoppinglist.domain.ShoppingListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindShoppingRepository(impl: ShopListRepositoryImpl): ShoppingListRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideShoppingListDao(application: Application): ShoppingListDao {
            return AppDatabase.getInstance(application).shoppingListDao()
        }
    }
}
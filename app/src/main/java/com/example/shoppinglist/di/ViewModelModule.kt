package com.example.shoppinglist.di

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.presentation.MainViewModel
import com.example.shoppinglist.presentation.ShoppingItemViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(impl: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShoppingItemViewModel::class)
    fun bindShoppingItemViewModel(impl: ShoppingItemViewModel): ViewModel
}
package com.example.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.DeleteShoppingItemUseCase
import com.example.shoppinglist.domain.EditShoppingItemUseCase
import com.example.shoppinglist.domain.GetShoppingItemListUseCase
import com.example.shoppinglist.domain.ShoppingItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShoppingListUseCase = GetShoppingItemListUseCase(repository)
    private val deleteShoppingItemUseCase = DeleteShoppingItemUseCase(repository)
    private val editShoppingItemUseCase = EditShoppingItemUseCase(repository)

    var shoppingList = getShoppingListUseCase.getShopList()

    fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        viewModelScope.launch {
            deleteShoppingItemUseCase.deleteShoppingItem(shoppingItem)
        }
    }

    fun changeEnableState(shoppingItem: ShoppingItem) {
        viewModelScope.launch {
            var newShoppingItem = shoppingItem.copy(enabled = !shoppingItem.enabled)
            editShoppingItemUseCase.editShoppingItem(newShoppingItem)
        }
    }
}
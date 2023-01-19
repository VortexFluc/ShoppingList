package com.example.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.DeleteShoppingItemUseCase
import com.example.shoppinglist.domain.EditShoppingItemUseCase
import com.example.shoppinglist.domain.GetShoppingItemListUseCase
import com.example.shoppinglist.domain.ShoppingItem

class MainViewModel: ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShoppingListUseCase = GetShoppingItemListUseCase(repository)
    private val deleteShoppingItemUseCase = DeleteShoppingItemUseCase(repository)
    private val editShoppingItemUseCase = EditShoppingItemUseCase(repository)

    var shoppingList = getShoppingListUseCase.getShopList()

    fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        deleteShoppingItemUseCase.deleteShoppingItem(shoppingItem)
    }

    fun changeEnableState(shoppingItem: ShoppingItem) {
        var newShoppingItem = shoppingItem.copy(enabled = !shoppingItem.enabled)
        editShoppingItemUseCase.editShoppingItem(newShoppingItem)
    }

}
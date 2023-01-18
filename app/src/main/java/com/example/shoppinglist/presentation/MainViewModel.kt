package com.example.shoppinglist.presentation

import androidx.lifecycle.MutableLiveData
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

    var shoppingList = MutableLiveData<List<ShoppingItem>>()

    fun getShoppingList() {
        val list = getShoppingListUseCase.getShopList()
        shoppingList.value = list
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        deleteShoppingItemUseCase.deleteShoppingItem(shoppingItem)
        getShoppingList()
    }

    fun changeEnableState(shoppingItem: ShoppingItem) {
        var newShoppintItem = shoppingItem.copy(enabled = !shoppingItem.enabled)
        editShoppingItemUseCase.editShoppingItem(newShoppintItem)
        getShoppingList()
    }

}
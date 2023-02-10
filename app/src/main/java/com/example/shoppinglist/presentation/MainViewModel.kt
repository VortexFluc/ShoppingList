package com.example.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.domain.DeleteShoppingItemUseCase
import com.example.shoppinglist.domain.EditShoppingItemUseCase
import com.example.shoppinglist.domain.GetShoppingItemListUseCase
import com.example.shoppinglist.domain.ShoppingItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getShoppingListUseCase: GetShoppingItemListUseCase,
    private val deleteShoppingItemUseCase: DeleteShoppingItemUseCase,
    private val editShoppingItemUseCase: EditShoppingItemUseCase
) : ViewModel() {

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
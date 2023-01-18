package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

class GetShoppingItemListUseCase(private val shoppingListRepository: ShoppingListRepository) {

    fun getShopList(): LiveData<List<ShoppingItem>> {
       return shoppingListRepository.getShoppingList()
    }
}
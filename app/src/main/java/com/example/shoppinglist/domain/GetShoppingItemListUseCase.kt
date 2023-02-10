package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetShoppingItemListUseCase @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository
    ) {

    fun getShopList(): LiveData<List<ShoppingItem>> {
       return shoppingListRepository.getShoppingList()
    }
}
package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShoppingListRepository {
    fun addShoppingItem(shoppingItem: ShoppingItem)
    fun deleteShoppingItem(shoppingItem: ShoppingItem)
    fun editShoppingItem(shoppingItem: ShoppingItem)
    fun getShoppingList(): LiveData<List<ShoppingItem>>
    fun getShoppingItem(id: Int): ShoppingItem
}
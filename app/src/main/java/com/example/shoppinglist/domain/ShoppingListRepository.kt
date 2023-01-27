package com.example.shoppinglist.domain

import androidx.lifecycle.LiveData

interface ShoppingListRepository {
    suspend fun addShoppingItem(shoppingItem: ShoppingItem)
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)
    suspend fun editShoppingItem(shoppingItem: ShoppingItem)
    fun getShoppingList(): LiveData<List<ShoppingItem>>
    suspend fun getShoppingItem(id: Int): ShoppingItem
}
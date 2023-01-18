package com.example.shoppinglist.domain

interface ShoppingListRepository {
    fun addShoppingItem(shoppingItem: ShoppingItem)
    fun deleteShoppingItem(shoppingItem: ShoppingItem)
    fun editShoppingItem(shoppingItem: ShoppingItem)
    fun getShoppingList(): List<ShoppingItem>
    fun getShoppingItem(id: Int): ShoppingItem
}
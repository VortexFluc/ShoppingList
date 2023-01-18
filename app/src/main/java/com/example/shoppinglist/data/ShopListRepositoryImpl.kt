package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ShoppingItem
import com.example.shoppinglist.domain.ShoppingListRepository

object ShopListRepositoryImpl: ShoppingListRepository{

    private val shopList = mutableListOf<ShoppingItem>()
    private var autoIncrementId = 0

    override fun addShoppingItem(shoppingItem: ShoppingItem) {
        if (shoppingItem.id == ShoppingItem.UNDEFINED_ID) {
            shoppingItem.id = autoIncrementId++
        }
        shopList.add(shoppingItem)
    }

    override fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shopList.remove(shoppingItem)
    }

    override fun editShoppingItem(shoppingItem: ShoppingItem) {
        val oldEl = getShoppingItem(shoppingItem.id)
        deleteShoppingItem(oldEl)
        addShoppingItem(shoppingItem)
    }

    override fun getShoppingList(): List<ShoppingItem> {
        return shopList.toList()
    }

    override fun getShoppingItem(id: Int): ShoppingItem {
        return shopList.find {
            it.id == id
        } ?: throw RuntimeException("Element with id $id not found")
    }
}
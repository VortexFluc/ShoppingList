package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.domain.ShoppingItem
import com.example.shoppinglist.domain.ShoppingListRepository

object ShopListRepositoryImpl: ShoppingListRepository{

    private val shopListLD = MutableLiveData<List<ShoppingItem>>()
    private val shopList = sortedSetOf<ShoppingItem>({ p0, p1 -> p0.id.compareTo(p1.id) })
    private var autoIncrementId = 0

    init {
        for (i in 0 until 1000) {
            val item = ShoppingItem("Name $i", i, true)
            addShoppingItem(item)
        }
    }

    override fun addShoppingItem(shoppingItem: ShoppingItem) {
        if (shoppingItem.id == ShoppingItem.UNDEFINED_ID) {
            shoppingItem.id = autoIncrementId++
        }
        shopList.add(shoppingItem)
        updateList()
    }

    override fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shopList.remove(shoppingItem)
        updateList()
    }

    override fun editShoppingItem(shoppingItem: ShoppingItem) {
        val oldEl = getShoppingItem(shoppingItem.id)
        deleteShoppingItem(oldEl)
        addShoppingItem(shoppingItem)
    }

    override fun getShoppingList(): LiveData<List<ShoppingItem>> {
        return shopListLD
    }

    override fun getShoppingItem(id: Int): ShoppingItem {
        return shopList.find {
            it.id == id
        } ?: throw RuntimeException("Element with id $id not found")
    }

    private fun updateList() {
        shopListLD.value = shopList.toList()
    }
}
package com.example.shoppinglist.domain

class GetShoppingItemListUseCase(private val shoppingListRepository: ShoppingListRepository) {

    fun getShopList(): List<ShoppingItem> = shoppingListRepository.getShoppingList()
}
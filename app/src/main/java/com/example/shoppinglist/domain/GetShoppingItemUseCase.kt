package com.example.shoppinglist.domain

class GetShoppingItemUseCase(private val shoppingListRepository: ShoppingListRepository) {
    suspend fun getShoppingItem(id: Int): ShoppingItem = shoppingListRepository.getShoppingItem(id)
}
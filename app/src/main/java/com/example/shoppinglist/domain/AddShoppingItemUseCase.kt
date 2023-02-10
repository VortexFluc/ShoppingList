package com.example.shoppinglist.domain

import javax.inject.Inject

class AddShoppingItemUseCase @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository
) {
    suspend fun addShoppingItem(shoppingItem: ShoppingItem) {
        shoppingListRepository.addShoppingItem(shoppingItem)
    }
}
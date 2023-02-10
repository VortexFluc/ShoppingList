package com.example.shoppinglist.domain

import javax.inject.Inject

class DeleteShoppingItemUseCase @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository
    ) {
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingListRepository.deleteShoppingItem(shoppingItem)
    }
}
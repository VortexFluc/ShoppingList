package com.example.shoppinglist.domain

import javax.inject.Inject

class EditShoppingItemUseCase @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository
    ) {
    suspend fun editShoppingItem(shoppingItem: ShoppingItem) {
        shoppingListRepository.editShoppingItem(shoppingItem)
    }
}
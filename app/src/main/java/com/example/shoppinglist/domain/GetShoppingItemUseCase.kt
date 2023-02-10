package com.example.shoppinglist.domain

import javax.inject.Inject

class GetShoppingItemUseCase @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository
) {
    suspend fun getShoppingItem(id: Int): ShoppingItem = shoppingListRepository.getShoppingItem(id)
}
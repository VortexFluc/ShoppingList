package com.example.shoppinglist.domain

class GetShoppintItemUseCase(private val shoppingListRepository: ShoppingListRepository) {
    fun getShoppingItem(id: Int): ShoppingItem = shoppingListRepository.getShoppingItem(id)

}
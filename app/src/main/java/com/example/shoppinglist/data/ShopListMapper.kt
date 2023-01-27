package com.example.shoppinglist.data

import com.example.shoppinglist.domain.ShoppingItem

class ShopListMapper {
    fun mapEntityToDbModel(shoppingItem: ShoppingItem) =
        ShoppingItemDbModel(
            id = shoppingItem.id,
            name = shoppingItem.name,
            count = shoppingItem.count,
            enabled = shoppingItem.enabled
        )

    fun mapDbModelToEntity(shoppingItemDbModel: ShoppingItemDbModel) =
        ShoppingItem(
            id = shoppingItemDbModel.id,
            name = shoppingItemDbModel.name,
            count = shoppingItemDbModel.count,
            enabled = shoppingItemDbModel.enabled
        )

    fun mapListDbModelToListEntity(list: List<ShoppingItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}
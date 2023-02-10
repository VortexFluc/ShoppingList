package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.shoppinglist.domain.ShoppingItem
import com.example.shoppinglist.domain.ShoppingListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val mapper: ShopListMapper,
    private val shoppingListDao: ShoppingListDao,
) : ShoppingListRepository {

    override suspend fun addShoppingItem(shoppingItem: ShoppingItem) {
        shoppingListDao.addShoppingItem(mapper.mapEntityToDbModel(shoppingItem))
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingListDao.deleteShoppingItem(mapper.mapEntityToDbModel(shoppingItem).id)
    }

    override suspend fun editShoppingItem(shoppingItem: ShoppingItem) {
        shoppingListDao.addShoppingItem(mapper.mapEntityToDbModel(shoppingItem))
    }

    override fun getShoppingList(): LiveData<List<ShoppingItem>> =
        Transformations.map(shoppingListDao.getShopingList()) {
            mapper.mapListDbModelToListEntity(it)
        }


    override suspend fun getShoppingItem(id: Int): ShoppingItem {
        val dbModel = shoppingListDao.getShoppingItem(id)
        return mapper.mapDbModelToEntity(dbModel)
    }
}
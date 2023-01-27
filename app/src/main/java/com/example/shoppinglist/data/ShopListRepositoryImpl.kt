package com.example.shoppinglist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.shoppinglist.domain.ShoppingItem
import com.example.shoppinglist.domain.ShoppingListRepository
import kotlin.random.Random

class ShopListRepositoryImpl(
    application: Application
) : ShoppingListRepository {

    private val shoppingListDao = AppDatabase.getInstance(application).shoppingListDao()
    private val mapper = ShopListMapper()

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
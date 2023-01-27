package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShoppingListDao {
    @Query("SELECT * FROM shopping_items")
    fun getShopingList(): LiveData<List<ShoppingItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShoppingItem(shoppingItemDbModel: ShoppingItemDbModel)

    @Query("DELETE FROM shopping_items WHERE id = :shoppingItemId")
    fun deleteShoppingItem(shoppingItemId: Int)

    @Query("SELECT * FROM shopping_items WHERE id = :shoppingItemId LIMIT 1")
    fun getShoppingItem(shoppingItemId: Int): ShoppingItemDbModel
}
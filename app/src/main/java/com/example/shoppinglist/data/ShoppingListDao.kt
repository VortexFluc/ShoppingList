package com.example.shoppinglist.data

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShoppingListDao {
    @Query("SELECT * FROM shopping_items")
    fun getShopingList(): LiveData<List<ShoppingItemDbModel>>

    @Query("SELECT * FROM shopping_items")
    fun getShopingListCursor(): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShoppingItem(shoppingItemDbModel: ShoppingItemDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShoppingItemSync(shoppingItemDbModel: ShoppingItemDbModel)

    @Query("DELETE FROM shopping_items WHERE id = :shoppingItemId")
    suspend fun deleteShoppingItem(shoppingItemId: Int)

    @Query("SELECT * FROM shopping_items WHERE id = :shoppingItemId LIMIT 1")
    suspend fun getShoppingItem(shoppingItemId: Int): ShoppingItemDbModel
}
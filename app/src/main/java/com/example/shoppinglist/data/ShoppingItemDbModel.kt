package com.example.shoppinglist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItemDbModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val count: Int,
    val enabled: Boolean
)
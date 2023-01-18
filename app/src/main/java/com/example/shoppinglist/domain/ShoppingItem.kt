package com.example.shoppinglist.domain

data class ShoppingItem(
    val id: Int,
    val name: String,
    val count: Int,
    val enables: Boolean
)
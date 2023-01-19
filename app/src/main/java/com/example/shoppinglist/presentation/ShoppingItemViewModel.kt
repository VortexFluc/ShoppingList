package com.example.shoppinglist.presentation

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.AddShoppingItemUseCase
import com.example.shoppinglist.domain.EditShoppingItemUseCase
import com.example.shoppinglist.domain.GetShoppingItemUseCase
import com.example.shoppinglist.domain.ShoppingItem

class ShoppingItemViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl

    private val getShoppingItemUseCase = GetShoppingItemUseCase(repository)
    private val addShoppingItemUseCase = AddShoppingItemUseCase(repository)
    private val editShoppingItemUseCase = EditShoppingItemUseCase(repository)

    fun getShoppingItem(id: Int): ShoppingItem = getShoppingItemUseCase.getShoppingItem(id)

    fun changeShoppingItemData(inputName: String?, inputCount: String?, id: Int) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val shoppingItem = ShoppingItem(name, count, true, id)
            editShoppingItemUseCase.editShoppingItem(shoppingItem)
        }

    }

    fun addShoppingItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            val shoppingItem = ShoppingItem(name, count, true)
            addShoppingItemUseCase.addShoppingItem(shoppingItem)
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            TODO("Show error input name")
            result = false
        }

        if (count <= 0) {
            TODO("Show error input name")
            result = false
        }

        return result
    }
 }
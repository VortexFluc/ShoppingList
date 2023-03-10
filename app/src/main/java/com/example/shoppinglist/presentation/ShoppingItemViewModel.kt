package com.example.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.domain.AddShoppingItemUseCase
import com.example.shoppinglist.domain.EditShoppingItemUseCase
import com.example.shoppinglist.domain.GetShoppingItemUseCase
import com.example.shoppinglist.domain.ShoppingItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShoppingItemViewModel @Inject constructor(
    private val getShoppingItemUseCase: GetShoppingItemUseCase,
    private val addShoppingItemUseCase: AddShoppingItemUseCase,
    private val editShoppingItemUseCase: EditShoppingItemUseCase
) : ViewModel() {

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shoppingItem = MutableLiveData<ShoppingItem>()
    val shoppingItem: LiveData<ShoppingItem>
        get() = _shoppingItem

    private val _canClose = MutableLiveData<Unit>()
    val canClose: LiveData<Unit>
        get() = _canClose

    fun getShoppingItem(id: Int) {
        viewModelScope.launch {
            val item = getShoppingItemUseCase.getShoppingItem(id)
            _shoppingItem.value = item
        }
    }

    fun changeShoppingItemData(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            _shoppingItem.value?.let {
                viewModelScope.launch {
                    val item = it.copy(name = name, count = count)
                    editShoppingItemUseCase.editShoppingItem(item)
                    finishWork()
                }
            }
        }

    }

    fun addShoppingItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid) {
            viewModelScope.launch {
                val shoppingItem = ShoppingItem(name, count, true)
                addShoppingItemUseCase.addShoppingItem(shoppingItem)
                finishWork()
            }
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
            _errorInputName.value = true
            result = false
        }

        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }

        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _canClose.value = Unit
    }
}
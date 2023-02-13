package com.example.shoppinglist.data

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log


class ShopListProvider: ContentProvider() {

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI("com.example.shoppinglist", "/shop_items", GET_SHOP_ITEMS_QUERY)
        addURI("com.example.shoppinglist", "/shop_items/#", GET_SHOP_ITEM_BY_ID_QUERY)
        addURI("com.example.shoppinglist", "/shop_items/*", GET_SHOP_ITEM_BY_NAME_QUERY)
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val code = uriMatcher.match(uri)
        when (code) {
            GET_SHOP_ITEMS_QUERY -> {
                Log.d("ShopListProvider", "code: $code")
            }
            GET_SHOP_ITEM_BY_ID_QUERY -> {
                Log.d("ShopListProvider", "code: $code")
            }

            GET_SHOP_ITEM_BY_NAME_QUERY -> {
                Log.d("ShopListProvider", "code: $code")
            }
            UriMatcher.NO_MATCH -> {
                Log.d("ShopListProvider", "No match!")
            }
        }
        Log.d("ShopListProvider", "query: $uri code: $code")
        return null
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    companion object {
        private const val GET_SHOP_ITEMS_QUERY = 100
        private const val GET_SHOP_ITEM_BY_ID_QUERY = 101
        private const val GET_SHOP_ITEM_BY_NAME_QUERY = 102

    }
}
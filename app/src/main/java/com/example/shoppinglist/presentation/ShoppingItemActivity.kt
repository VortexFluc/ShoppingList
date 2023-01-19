package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.R

class ShoppingItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping_item)
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        Log.d(TAG, mode.toString())
    }

    companion object {
        private const val TAG = "ShoppingItemActivity"

        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOPPING_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"

        fun newIntentEdit(context: Context, shoppingItemId: Int): Intent {
            val intent = Intent(context, ShoppingItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOPPING_ITEM_ID, shoppingItemId)
            return intent
        }

        fun newIntentAdd(context: Context): Intent {
            val intent = Intent(context, ShoppingItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }
    }
}
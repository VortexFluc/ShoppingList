package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShoppingItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shoppingListAdapter: ShoppingListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupRecyclerView()

        viewModel.shoppingList.observe(this) {
            shoppingListAdapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        val rvShopList: RecyclerView = findViewById(R.id.rv_shop_list)
        shoppingListAdapter = ShoppingListAdapter()
        with(rvShopList) {
            adapter = shoppingListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShoppingListAdapter.ITEM_ENABLED,
                ShoppingListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShoppingListAdapter.ITEM_DISABLED,
                ShoppingListAdapter.MAX_POOL_SIZE
            )
        }

        setupLongClickListener()

        setupClickListener()

        setupSwipeListener(rvShopList)
    }

    private fun setupSwipeListener(rvShopList: RecyclerView) {
        val itemTouchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteShoppingItem(shoppingListAdapter.currentList[viewHolder.adapterPosition])
            }
        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun setupClickListener() {
        shoppingListAdapter.onShopItemClickListener = {
            Log.d(TAG, it.toString())
        }
    }

    private fun setupLongClickListener() {
        shoppingListAdapter.onShopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }


    companion object {
        private const val TAG = "MainActivity"
    }
}
package com.example.shoppinglist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var shoppingListAdapter: ShoppingListAdapter
    private lateinit var fabAddItem: FloatingActionButton

    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        shopItemContainer = findViewById(R.id.shop_item_container)


        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupRecyclerView()

        viewModel.shoppingList.observe(this) {
            shoppingListAdapter.submitList(it)
        }
    }

    private fun isLandMode() = shopItemContainer != null

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

        if (isLandMode()) {
            setupLandClickListener()
        } else {
            setupPaneClickListener()
        }

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

    private fun setupPaneClickListener() {
        shoppingListAdapter.onShopItemClickListener = {
            val intent = ShoppingItemActivity.newIntentEdit(this, it.id)
            startActivity(intent)
        }

        fabAddItem = findViewById(R.id.fab_add_shop_item)
        fabAddItem.setOnClickListener {
            val intent = ShoppingItemActivity.newIntentAdd(this)
            startActivity(intent)
        }
    }

    private fun setupLandClickListener() {
        shoppingListAdapter.onShopItemClickListener = {
            val fragment = ShoppingItemFragment.newInstanceEditItem(it.id)
            startFragment(fragment)
        }

        fabAddItem = findViewById(R.id.fab_add_shop_item)
        fabAddItem.setOnClickListener {
            val fragment = ShoppingItemFragment.newInstanceAddItem()
            startFragment(fragment)
        }

    }

    private fun startFragment(fragment: ShoppingItemFragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .add(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
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
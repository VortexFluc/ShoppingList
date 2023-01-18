package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShoppingItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var llShopList: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        llShopList = findViewById(R.id.ll_shop_list)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.shoppingList.observe(this) {
            showList(it)
        }
    }

    private fun showList(list: List<ShoppingItem>) {
        llShopList.removeAllViews()
        for (item in list) {
            val layoutId = if (item.enabled) R.layout.item_shop_enabled else R.layout.item_shop_disabled
            val view = LayoutInflater.from(this).inflate(layoutId, llShopList, false)
            val tvName: TextView = view.findViewById(R.id.tv_name)
            val tvCount: TextView = view.findViewById(R.id.tv_count)

            tvName.text = item.name
            tvCount.text = item.count.toString()

            view.setOnLongClickListener {
                viewModel.changeEnableState(item)
                true
            }
            llShopList.addView(view)
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShoppingItem

class ShoppingListAdapter : RecyclerView.Adapter<ShoppingListAdapter.ShoppingItemViewHolder>() {

    var shopList = listOf<ShoppingItem>()
        set(value) {
            val callback = ShoppingListDiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
            field = value
        }

    var onShopItemLongClickListener: ((ShoppingItem) -> Unit)? = null
    var onShopItemClickListener: ((ShoppingItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(
                    viewType,
                    parent,
                    false
                )
        return ShoppingItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val item = shopList[position]
        val status = if (item.enabled) "Active" else "Innactive"
        with(holder) {
            tvName.text = "${item.name} $status"
            tvCount.text = item.count.toString()
            holder.itemView.setOnLongClickListener {
                onShopItemLongClickListener?.invoke(item)
                true
            }

            holder.itemView.setOnClickListener {
                onShopItemClickListener?.invoke(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (shopList[position].enabled) ITEM_ENABLED else ITEM_DISABLED
    }

    override fun getItemCount(): Int = shopList.size

    class ShoppingItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvCount: TextView = view.findViewById(R.id.tv_count)
    }

    companion object {
        const val ITEM_DISABLED = R.layout.item_shop_disabled
        const val ITEM_ENABLED = R.layout.item_shop_enabled

        const val MAX_POOL_SIZE = 15
    }
}
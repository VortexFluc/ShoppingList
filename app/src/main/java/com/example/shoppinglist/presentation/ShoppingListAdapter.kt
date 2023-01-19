package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShoppingItem

class ShoppingListAdapter :
    ListAdapter<ShoppingItem, ShoppingItemViewHolder>(ShoppingItemDiffCallback()) {

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
        val item = getItem(position)
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
        return if (getItem(position).enabled) ITEM_ENABLED else ITEM_DISABLED
    }

    companion object {
        const val ITEM_DISABLED = R.layout.item_shop_disabled
        const val ITEM_ENABLED = R.layout.item_shop_enabled

        const val MAX_POOL_SIZE = 15
    }
}
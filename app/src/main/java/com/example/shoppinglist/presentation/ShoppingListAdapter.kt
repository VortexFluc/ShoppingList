package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShoppingItem

class ShoppingListAdapter : RecyclerView.Adapter<ShoppingListAdapter.ShoppingItemViewHolder>() {

    var shopList = listOf<ShoppingItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        val view =
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_shop_disabled,
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
                true
            }

            if (item.enabled) {
                holder.tvName.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.holo_red_light))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int = shopList.size

    class ShoppingItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvCount: TextView = view.findViewById(R.id.tv_count)
    }
}
package com.kodego.diangca.ebrahim.myslambook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kodego.diangca.ebrahim.myslambook.databinding.RecycleViewSingleItemBinding

class SimpleStringAdapter(private val items: ArrayList<String>) :
    RecyclerView.Adapter<SimpleStringAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecycleViewSingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.itemName.text = items[position]
        holder.binding.btnRemove.setOnClickListener {
            items.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, items.size)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(val binding: RecycleViewSingleItemBinding) : RecyclerView.ViewHolder(binding.root)
}

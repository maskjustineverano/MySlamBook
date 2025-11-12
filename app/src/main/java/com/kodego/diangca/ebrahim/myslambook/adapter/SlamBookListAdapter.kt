package com.kodego.diangca.ebrahim.myslambook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kodego.diangca.ebrahim.myslambook.databinding.SlamBookItemBinding
import com.kodego.diangca.ebrahim.myslambook.model.SlamBook

class SlamBookListAdapter(
    private val onEdit: (SlamBook) -> Unit,
    private val onDelete: (SlamBook) -> Unit
) : ListAdapter<SlamBook, SlamBookListAdapter.SlamBookViewHolder>(SlamBookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlamBookViewHolder {
        val binding = SlamBookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SlamBookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SlamBookViewHolder, position: Int) {
        val slamBook = getItem(position)
        holder.bind(slamBook)
    }

    inner class SlamBookViewHolder(private val binding: SlamBookItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(slamBook: SlamBook) {
            binding.nameTextView.text = slamBook.fullName
            binding.editButton.setOnClickListener { onEdit(slamBook) }
            binding.deleteButton.setOnClickListener { onDelete(slamBook) }
        }
    }
}

class SlamBookDiffCallback : DiffUtil.ItemCallback<SlamBook>() {
    override fun areItemsTheSame(oldItem: SlamBook, newItem: SlamBook): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SlamBook, newItem: SlamBook): Boolean {
        return oldItem == newItem
    }
}

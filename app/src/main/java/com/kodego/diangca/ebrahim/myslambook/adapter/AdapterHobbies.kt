package com.kodego.diangca.ebrahim.myslambook.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kodego.diangca.ebrahim.myslambook.databinding.RecycleViewSingleItemBinding

class AdapterHobbies(private val context: Context, private var hobbies: ArrayList<String>) :
    RecyclerView.Adapter<AdapterHobbies.HobbiesViewHolder>() {

    inner class HobbiesViewHolder(
        private val itemBinding: RecycleViewSingleItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        fun bindHobby(hobby: String) {
            itemBinding.itemName.text = hobby

            itemBinding.btnRemove.setOnClickListener {
                btnRemoveOnClickListener(adapterPosition)
            }
        }

        override fun onClick(view: View?) {
            if (view != null)
                Snackbar.make(
                    itemBinding.root,
                    itemBinding.itemName.text,
                    Snackbar.LENGTH_SHORT
                ).show()
        }
    }

    private fun btnRemoveOnClickListener(position: Int) {
        removeHobby(position)
    }

    private fun removeHobby(position: Int) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Delete?")
        alertDialogBuilder.setMessage("Are you sure you want to delete ${hobbies[position]}?")
        alertDialogBuilder.setNegativeButton("Cancel", null)
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            hobbies.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
            Snackbar.make(
                itemView, // Assuming itemView is accessible here, otherwise pass the view
                "Hobby has been successfully removed.",
                Snackbar.LENGTH_SHORT
            ).show()
        }.show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HobbiesViewHolder {
        val itemBinding =
            RecycleViewSingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HobbiesViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return hobbies.size
    }

    override fun onBindViewHolder(holder: HobbiesViewHolder, position: Int) {
        holder.bindHobby(hobbies[position])
    }

    private val itemView: View
        get() = View(context)
}

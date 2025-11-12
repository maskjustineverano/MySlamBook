package com.kodego.diangca.ebrahim.myslambook.adapter

import Movie
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kodego.diangca.ebrahim.myslambook.databinding.RecycleViewSingleItemBinding

class AdapterMovie(var context: Context, var movie: ArrayList<Movie>) :
    RecyclerView.Adapter<AdapterMovie.MovieViewHolder>() {


    inner class MovieViewHolder(
        private val itemBinding: RecycleViewSingleItemBinding
    ) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {


        fun bindStudent(movie: Movie) {
            itemBinding.itemName.text = movie.title

            itemBinding.btnRemove.setOnClickListener {
                btnRemoveOnClickListener(itemBinding, adapterPosition)
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

    private fun btnRemoveOnClickListener(
        itemBinding: RecycleViewSingleItemBinding,
        positionAdapter: Int
    ) {

        removeSong(itemBinding, positionAdapter)
    }

    private fun removeSong(itemBinding: RecycleViewSingleItemBinding, positionAdapter: Int) {
        AlertDialog.Builder(context)
            .setTitle("Delete?")
            .setMessage("Are you sure you want to delete ${movie[positionAdapter].title}?")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Yes") { _, _ ->
                movie.removeAt(positionAdapter)
                notifyItemRemoved(positionAdapter)
                notifyItemRangeChanged(positionAdapter, itemCount)
                Snackbar.make(
                    itemBinding.root,
                    "Movie has been successfully removed.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            .show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding =
            RecycleViewSingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return movie.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindStudent(movie[position])
    }
}
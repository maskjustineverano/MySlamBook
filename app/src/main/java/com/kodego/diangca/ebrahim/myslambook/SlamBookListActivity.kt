package com.kodego.diangca.ebrahim.myslambook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kodego.diangca.ebrahim.myslambook.adapter.SlamBookListAdapter
import com.kodego.diangca.ebrahim.myslambook.databinding.ActivitySlamBookListBinding
import com.kodego.diangca.ebrahim.myslambook.model.SlamBook
import com.kodego.diangca.ebrahim.myslambook.model.SlamBookViewModel

class SlamBookListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySlamBookListBinding
    private lateinit var slamBookViewModel: SlamBookViewModel
    private lateinit var slamBookListAdapter: SlamBookListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySlamBookListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        slamBookViewModel = ViewModelProvider(this).get(SlamBookViewModel::class.java)

        setupRecyclerView()

        slamBookViewModel.readAllData.observe(this) { slamBooks ->
            slamBookListAdapter.submitList(slamBooks)
        }
    }

    private fun setupRecyclerView() {
        slamBookListAdapter = SlamBookListAdapter(
            onEdit = { slamBook ->
                val intent = Intent(this, FormActivity::class.java)
                intent.putExtra("slamBook", slamBook)
                startActivity(intent)
            },
            onDelete = { slamBook ->
                slamBookViewModel.deleteSlamBook(slamBook)
            }
        )
        binding.recyclerView.apply {
            adapter = slamBookListAdapter
            layoutManager = LinearLayoutManager(this@SlamBookListActivity)
        }
    }
}
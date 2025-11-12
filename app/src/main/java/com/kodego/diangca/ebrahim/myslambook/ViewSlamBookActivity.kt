package com.kodego.diangca.ebrahim.myslambook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.kodego.diangca.ebrahim.myslambook.databinding.ActivityViewSlamBookBinding
import com.kodego.diangca.ebrahim.myslambook.model.SlamBook
import com.kodego.diangca.ebrahim.myslambook.model.SlamBookViewModel

class ViewSlamBookActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewSlamBookBinding
    private lateinit var slamBookViewModel: SlamBookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewSlamBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val slamBookId = intent.getIntExtra("slam_book_id", -1)

        slamBookViewModel = ViewModelProvider(this).get(SlamBookViewModel::class.java)
        slamBookViewModel.readAllData.observe(this) { slamBooks ->
            val slamBook = slamBooks.find { it.id == slamBookId }
            slamBook?.let { displaySlamBook(it) }
        }
    }

    private fun displaySlamBook(slamBook: SlamBook) {
        binding.fullName.text = slamBook.fullName
        binding.nickName.text = slamBook.nickName
        binding.birthDate.text = slamBook.birthDate
        binding.gender.text = slamBook.gender
        binding.status.text = slamBook.status
        binding.emailAdd.text = slamBook.emailAdd
        binding.contactNo.text = slamBook.contactNo
        binding.address.text = slamBook.address
        binding.motto.text = slamBook.motto
    }
}
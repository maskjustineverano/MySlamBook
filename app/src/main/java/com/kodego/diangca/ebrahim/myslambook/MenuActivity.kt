package com.kodego.diangca.ebrahim.myslambook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kodego.diangca.ebrahim.myslambook.databinding.ActivityMenuBinding
import com.kodego.diangca.ebrahim.myslambook.model.SlamBook

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreate.setOnClickListener {
            btnCreateOnClickListener()
        }

        binding.btnView.setOnClickListener {
            btnViewOnClickListener()
        }
    }

    private fun btnCreateOnClickListener() {
        val nextForm = Intent(this, FormActivity::class.java)

        nextForm.putExtra("slamBook", SlamBook())
        startActivity(nextForm)
    }

    private fun btnViewOnClickListener() {
        val intent = Intent(this, SlamBookListActivity::class.java)
        startActivity(intent)
    }
}
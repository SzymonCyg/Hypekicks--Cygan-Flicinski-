package com.example.hypekicks

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.hypekicks.databinding.ActivitySneakerDetailBinding
import com.example.hypekicks.model.Sneaker

class SneakerDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySneakerDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySneakerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.detailsCloseButton.setOnClickListener {
            finish()
        }


        val sneaker = intent.getSerializableExtra("SNEAKER") as? Sneaker


        sneaker?.let {
            binding.tvBrandNModel.text = "${it.brand} ${it.modelName}"
            binding.tvReleaseYear.text = it.releaseYear.toString()
            binding.tvPrice.text = "${it.resellPrice} zł"

            Glide.with(this)
                .load(it.imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(binding.ivSneakerDetails)
        }
    }
}
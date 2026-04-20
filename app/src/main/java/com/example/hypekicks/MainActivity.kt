package com.example.hypekicks

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hypekicks.adapter.SneakerAdapter
import com.example.hypekicks.databinding.ActivityMainBinding
import com.example.hypekicks.model.Sneaker

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.recyclerSneakers.layoutManager = GridLayoutManager(this, 2)
        val testData = listOf(
            Sneaker("", "Nike", "Air Force 1", 500.0, 2020, ""),
            Sneaker("", "Adidas", "Yeezy 350", 1200.0, 2021, "")
        )
        val adapter = SneakerAdapter(testData) { }
        binding.recyclerSneakers.adapter = adapter
    }
}
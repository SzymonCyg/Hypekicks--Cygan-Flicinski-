package com.example.hypekicks

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hypekicks.adapter.SneakerAdapter
import com.example.hypekicks.databinding.ActivityMainBinding
import com.example.hypekicks.repository.SneakerRepository


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SneakerAdapter
    private val repository = SneakerRepository()


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


        binding.adminOpenButton.setOnClickListener {
            val intent = android.content.Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }


        setupRecycler()
        loadData()
    }

    private fun setupRecycler() {
        adapter = SneakerAdapter(emptyList()) { sneaker ->
            // obsługa klikniecia na item
        }

        binding.recyclerSneakers.layoutManager =
            GridLayoutManager(this, 2)

        binding.recyclerSneakers.adapter = adapter
    }

    private fun loadData() {
        repository.getAllSneakersRealtime { list ->
            adapter.updateData(list)
        }
    }
}
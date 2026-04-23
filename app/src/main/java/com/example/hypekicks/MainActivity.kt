package com.example.hypekicks

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import com.example.hypekicks.adapter.SneakerAdapter
import com.example.hypekicks.databinding.ActivityMainBinding
import com.example.hypekicks.model.Sneaker
import com.example.hypekicks.repository.SneakerRepository


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SneakerAdapter
    private val repository = SneakerRepository()
    private var listaWszystkichButow: List<Sneaker> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.widokGlowny) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.przyciskPaneluAdmin.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
        }

        setupRecycler()
        konfigurujWyszukiwanie()
        loadData()
    }

    private fun setupRecycler() {
        adapter = SneakerAdapter(emptyList()) { sneaker ->
            val intent = Intent(this, SneakerDetailActivity::class.java)
            intent.putExtra("SNEAKER", sneaker)
            startActivity(intent)
        }

        binding.listaButow.layoutManager = GridLayoutManager(this, 2)
        binding.listaButow.adapter = adapter
    }

    private fun konfigurujWyszukiwanie() {
        binding.poleWyszukiwania.addTextChangedListener { tekst ->
            filtrujListe(tekst.toString())
        }
    }

    private fun filtrujListe(zapytanie: String) {
        val przefiltrowanaLista = if (zapytanie.isEmpty()) {
            listaWszystkichButow
        } else {
            listaWszystkichButow.filter { but ->
                but.brand.contains(zapytanie, ignoreCase = true) ||
                but.modelName.contains(zapytanie, ignoreCase = true)
            }
        }
        adapter.updateData(przefiltrowanaLista)
    }

    private fun loadData() {
        repository.getAllSneakersRealtime { list ->
            listaWszystkichButow = list
            filtrujListe(binding.poleWyszukiwania.text.toString())
        }
    }
}

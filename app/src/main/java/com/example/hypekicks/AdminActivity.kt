package com.example.hypekicks

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hypekicks.adapter.SneakerAdapter
import com.example.hypekicks.databinding.ActivityAdminBinding
import com.example.hypekicks.model.Sneaker
import com.example.hypekicks.repository.SneakerRepository


class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private val repository = SneakerRepository()
    private lateinit var adapter: SneakerAdapter
    private var edytowaneButy: Sneaker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.widokGlownyAdmin) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.przyciskZamknijAdmin.setOnClickListener {
            finish()
        }

        konfigurujRecyclerView()
        konfigurujFormularz()
        pobierzButy()
    }

    private fun konfigurujRecyclerView() {
        adapter = SneakerAdapter(emptyList()) { buty ->
            edytowaneButy = buty
            wypelnijFormularz(buty)
            binding.btnDodajButy.text = "Aktualizuj Dane"
        }
        binding.rvListaButowAdmin.layoutManager = LinearLayoutManager(this)
        binding.rvListaButowAdmin.adapter = adapter
    }

    private fun konfigurujFormularz() {
        binding.btnDodajButy.setOnClickListener {
            val marka = binding.etMarka.text.toString()
            val model = binding.etModel.text.toString()
            val cenaStr = binding.etCena.text.toString()
            val rokStr = binding.etRok.text.toString()
            val urlZdjecia = binding.etUrlZdjecia.text.toString()

            if (marka.isNotBlank() && model.isNotBlank() && cenaStr.isNotBlank() && rokStr.isNotBlank()) {
                val cena = cenaStr.toDoubleOrNull() ?: 0.0
                val rok = rokStr.toIntOrNull() ?: 0

                if (edytowaneButy != null) {
                    val zaktualizowaneButy = edytowaneButy!!.copy(
                        brand = marka,
                        modelName = model,
                        resellPrice = cena,
                        releaseYear = rok,
                        imageUrl = urlZdjecia
                    )
                    
                    repository.updateSneaker(zaktualizowaneButy) { sukces ->
                        if (sukces) wyczyscFormularz()
                    }
                } else {
                    val noweButy = Sneaker(
                        brand = marka,
                        modelName = model,
                        resellPrice = cena,
                        releaseYear = rok,
                        imageUrl = urlZdjecia
                    )

                    repository.addSneaker(noweButy) { sukces ->
                        if (sukces) wyczyscFormularz()
                    }
                }
            }
        }

        binding.btnUsunButy.setOnClickListener {
            edytowaneButy?.let { buty ->
                repository.deleteSneaker(buty.id) { sukces ->
                    if (sukces) wyczyscFormularz()
                }
            }
        }
    }

    private fun pobierzButy() {
        repository.getAllSneakersRealtime { lista ->
            adapter.updateData(lista)
        }
    }

    private fun wypelnijFormularz(buty: Sneaker) {
        binding.etMarka.setText(buty.brand)
        binding.etModel.setText(buty.modelName)
        binding.etCena.setText(buty.resellPrice.toString())
        binding.etRok.setText(buty.releaseYear.toString())
        binding.etUrlZdjecia.setText(buty.imageUrl)
    }

    private fun wyczyscFormularz() {
        binding.etMarka.text.clear()
        binding.etModel.text.clear()
        binding.etCena.text.clear()
        binding.etRok.text.clear()
        binding.etUrlZdjecia.text.clear()
        binding.btnDodajButy.text = "Dodaj Buty"
        edytowaneButy = null
    }
}

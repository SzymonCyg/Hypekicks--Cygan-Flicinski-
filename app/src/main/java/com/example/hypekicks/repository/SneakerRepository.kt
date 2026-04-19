package com.example.hypekicks.repository

import com.example.hypekicks.model.Sneaker
import com.google.firebase.firestore.FirebaseFirestore


class SneakerRepository {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("sneakers")


    fun addSneaker(sneaker: Sneaker, onResult: (Boolean) -> Unit) {
        val id = collection.document().id
        sneaker.id = id

        collection.document(id)
            .set(sneaker)
            .addOnSuccessListener {
                onResult(true)
            }
            .addOnFailureListener {
                onResult(false)
            }
    }


    fun getAllSneakers(onResult: (List<Sneaker>) -> Unit) {
        collection.get()
            .addOnSuccessListener { result ->
                val list = result.documents.mapNotNull {
                    it.toObject(Sneaker::class.java)
                }
                onResult(list)
            }
    }


    fun getSneakerById(id: String, onResult: (Sneaker?) -> Unit) {
        collection.document(id).get()
            .addOnSuccessListener {
                onResult(it.toObject(Sneaker::class.java))
            }
    }


    fun updateSneaker(sneaker: Sneaker, onResult: (Boolean) -> Unit) {
        collection.document(sneaker.id)
            .set(sneaker)
            .addOnSuccessListener {
                onResult(true)
            }
            .addOnFailureListener {
                onResult(false)
            }
    }


    fun deleteSneaker(id: String, onResult: (Boolean) -> Unit) {
        collection.document(id)
            .delete()
            .addOnSuccessListener {
                onResult(true)
            }
            .addOnFailureListener {
                onResult(false)
            }
    }
}
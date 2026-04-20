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


    fun getAllSneakersRealtime(onResult: (List<Sneaker>) -> Unit) {
        collection.addSnapshotListener { snapshot, error ->

            if (error != null || snapshot == null) {
                onResult(emptyList())
                return@addSnapshotListener
            }

            val list = snapshot.documents.mapNotNull { doc ->
                doc.toObject(Sneaker::class.java)?.apply {
                    id = doc.id
                }
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
package com.example.hypekicks.model

data class Sneaker(
    var id: String = "",
    var brand: String = "",
    var modelName: String = "",
    var resellPrice: Double = 0.0,
    var releaseYear: Int = 0,
    var imageUrl: String = ""
) : java.io.Serializable
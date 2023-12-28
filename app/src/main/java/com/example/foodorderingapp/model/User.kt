package com.example.foodorderingapp.model

data class User(
    val username: String,
    val fullName: String,
    val phoneNumer: String,
    val city: String
) {
    constructor() : this("", "", "", "")
}
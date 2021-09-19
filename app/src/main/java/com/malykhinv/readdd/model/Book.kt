package com.malykhinv.readdd.model

data class Book (
    val id: Int,
    val title: String,
    val author: String,
    val description: String?,
    val urlCover: String?,
    val year: Int?
)
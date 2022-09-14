package com.example.numbersfacts.data.local.models

import androidx.room.Entity

@Entity(tableName = "facts", primaryKeys = ["fact", "number"])
data class NumberFactEntity(
    val fact: String,
    val number: Int,
    val lastViewed: Long,
)

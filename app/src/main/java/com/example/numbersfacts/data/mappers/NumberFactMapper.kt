package com.example.numbersfacts.data.mappers

import com.example.numbersfacts.data.local.models.NumberFactEntity
import com.example.numbersfacts.domain.models.NumberFact

fun NumberFactEntity.toNumberFact() = NumberFact(
    fact=fact,
    number = number,
    lastViewed = lastViewed
)

fun NumberFact.toNumberFactEntity(newLastViewed: Long? = null) = NumberFactEntity(
    fact=fact,
    number = number,
    lastViewed = newLastViewed ?: lastViewed
)
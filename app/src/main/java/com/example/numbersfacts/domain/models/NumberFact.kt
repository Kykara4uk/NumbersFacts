package com.example.numbersfacts.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NumberFact(
    val number: Int,
    val fact: String,
    val lastViewed: Long
) : Parcelable

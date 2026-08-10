package com.elifnurozcelik.hw1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Selection(
    val name: String,
    val surname: String,
    val year: Int,
    var category: String? = null,
    var itemCount: Int? = null,
    var favorite: String? = null
) : Parcelable
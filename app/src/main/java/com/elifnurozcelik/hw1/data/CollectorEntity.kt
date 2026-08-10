package com.elifnurozcelik.hw1.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collectors")
data class CollectorEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val surname: String,
    val year: Int,

    val category: String,
    val itemCount: Int,
    val favorite: String
)


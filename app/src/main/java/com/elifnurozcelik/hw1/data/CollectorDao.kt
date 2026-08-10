package com.elifnurozcelik.hw1.data

import androidx.room.*

@Dao
interface CollectorDao {

    @Insert
    suspend fun insertCollector(collector: CollectorEntity)

    @Query("SELECT * FROM collectors ORDER BY year ASC")
    suspend fun getAllCollectors(): List<CollectorEntity>

    @Update
    suspend fun updateCollector(collector: CollectorEntity)

    @Delete
    suspend fun deleteCollector(collector: CollectorEntity)
}
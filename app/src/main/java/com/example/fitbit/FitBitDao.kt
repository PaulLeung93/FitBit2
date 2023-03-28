package com.example.fitbit

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NutritionDao {
    @Query("SELECT * FROM nutrition_table")
    fun getAll(): Flow<List<Nutrition>>

    @Insert
    fun insert(nutrition: Nutrition)

    @Delete
    fun deleteItem(nutrition: Nutrition)

    @Query("DELETE FROM nutrition_table")
    fun deleteAll()
}
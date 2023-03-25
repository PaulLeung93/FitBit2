package com.example.fitbit

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nutrition_table")

data class Nutrition(

    @ColumnInfo(name = "food") val food: String?,
    @ColumnInfo(name = "calories") val calories: String?,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
)
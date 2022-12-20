package com.example.weather.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.R

@Entity(tableName = "weather_entity_table")
data class WeatherEntity(
    @PrimaryKey( autoGenerate = true)
    val id: Long=0,
    val name: String="",
    val lat: Double=1.0,
    val lon: Double=1.0,
    var temperature: Int=0,
    var feelsLike: Int=1,
    var icon: Int= R.drawable.ic_russia
)

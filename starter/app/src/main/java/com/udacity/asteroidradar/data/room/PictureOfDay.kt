package com.udacity.asteroidradar.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
@Entity
data class PictureOfDay(@PrimaryKey (autoGenerate = false)
                        @Json(name = "media_type") val mediaType: String,
                        @Json(name = "url")  val image: String,
                        @Json(name = "explanation") val explanation : String)
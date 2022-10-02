package com.udacity.asteroidradar.data.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Asteroid")
data class Asteroid(
  @PrimaryKey(autoGenerate = false) val id: Long, val codename: String, val closeApproachDate: String,
  val absoluteMagnitude: Double, val estimatedDiameter: Double,
  val relativeVelocity: Double, val distanceFromEarth: Double,
  val isPotentiallyHazardous: Boolean) : Parcelable
package com.hskris.weatherapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City(val id: Int, val name: String, var country: String = "", var timezone: Long = 0) : Parcelable
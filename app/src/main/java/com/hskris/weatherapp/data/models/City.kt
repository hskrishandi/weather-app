package com.hskris.weatherapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class City(val id: Int, val name: String) : Parcelable {
    constructor() : this(-1, "")
}

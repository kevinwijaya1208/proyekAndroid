package com.example.proyekandroid

import android.os.Parcel
import android.os.Parcelable

data class FlightsData(
    var src : String,
    var desc : String,
    var skyID : String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(src)
        parcel.writeString(desc)
        parcel.writeString(skyID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FlightsData> {
        override fun createFromParcel(parcel: Parcel): FlightsData {
            return FlightsData(parcel)
        }

        override fun newArray(size: Int): Array<FlightsData?> {
            return arrayOfNulls(size)
        }
    }
}

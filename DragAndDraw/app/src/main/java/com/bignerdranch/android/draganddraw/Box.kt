package com.bignerdranch.android.draganddraw

import android.graphics.Point
import android.graphics.PointF
import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
class Box(val start: PointF, var end: PointF = start): Parcelable {

    private companion object : Parceler<Box> {
        override fun Box.write(parcel: Parcel, flags: Int) {
            start.writeToParcel(parcel, 0)
        }

        override fun create(parcel: Parcel): Box {
            // readFromParcel 사용하는거 같은데 잘 안됨...
            val point = PointF()
            point.readFromParcel(parcel)
            return Box(point)
        }
    }

    //var end: PointF = start

    val left: Float
        get() = Math.min(start.x, end.x)

    val right: Float
        get() = Math.max(start.x, end.x)

    val top: Float
        get() = Math.min(start.y, end.y)

    val bottom: Float
        get() = Math.max(start.y, end.y)
}
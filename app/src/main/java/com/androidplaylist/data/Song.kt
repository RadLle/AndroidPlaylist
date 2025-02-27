package com.androidplaylist.data

class Song (
    val name: String,
    val duration: Int,
    val artist: String
) {

    /** Return duration string with format XX:XX */
    fun getStringDuration(): String {
        val zeroIfNeedded = if ((duration % 60) < 10) "0" else ""
        return (duration / 60).toString() + ":" + zeroIfNeedded + (duration % 60).toString()
    }
}
package com.androidplaylist.data

import androidx.annotation.DrawableRes

class Playlist (
    val name: String,
    @DrawableRes val cover: Int,
    private val songs: MutableList<Song>
) {

    private fun countDuration(): Int {
        var s = 0
        for (song in songs) {
            s += song.duration
        }
        return s
    }

    private var duration = countDuration()

    /** Return duration string with format XX h XX min */
    fun getStringDuration(): String {
        if (duration > 3600) {
            return (duration / 3600).toString() + " h " + (duration / 60).toString() + " min"
        }
        return (duration / 60).toString() + " min"
    }

    /** Return song number string with format XX songs */
    fun getStringSongNumber(): String {
        return songs.size.toString() + " songs"
    }

    /** Add song into songs field */
    fun addSong(song: Song) {
        songs.add(song)
        duration += song.duration
    }

    fun getSongs(): List<Song> {
        return songs
    }
}
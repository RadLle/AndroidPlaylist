package com.androidplaylist.data

/** Global variable of current songs */
val songs = mutableListOf(
    Song("La Havane", 186, "Sofiane Pamart"),
    Song("Medellin", 375, "Sofiane Pamart"),
    Song("San Francisco", 175, "Sofiane Pamart"),
    Song("Apesanteur", 106, "REYN"),
    Song("Avant que je n'oublie", 121, "REYN"),
    Song("Wonderland", 179, "Paul-Marie Barbier"),

    Song("Gamesofluck", 348, "Parcels"),
    Song("Overnight", 220, "Parcels"),
    Song("Hideout", 266, "Parcels"),
    Song("Lightenup", 237, "Parcels"),
    Song("Giogrio by Moroder", 545, "Daft Punk"),
    Song("Give Life Back to Music", 275, "Daft Punk"),
    Song("J'y peux rien", 182, "Miel De Montagne")
)

class UndefinedSongException: Exception()

/** Look for a song in songs global variable
 * @param songName name of the song
 * @return if the song exists
 */
fun songExist(songName: String): Boolean {
    for (song in songs) {
        if (song.name.equals(songName)) {
            return true
        }
    }
    return false
}

/** Return song named songName
 * @throws UndefinedSongException if there is no song named songName in songs global variable
 */
fun getSong(songName: String): Song {
    for (song in songs) {
        if (song.name.equals(songName)) {
            return song
        }
    }
    throw UndefinedSongException()
}


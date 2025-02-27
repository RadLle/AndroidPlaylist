package com.androidplaylist.data

import com.androidplaylist.R

private val sweetPiano = Playlist (
    name = "Sweet Piano",
    cover = R.drawable.ic_launcher_background,
    songs = mutableListOf(
        songs[0], songs[1], songs[2],
        songs[3], songs[4], songs[5]
    )
)

private val funkyTracks = Playlist (
    name = "Funky Tracks",
    cover = R.drawable.ic_launcher_background,
    songs = mutableListOf(
        songs[6], songs[7], songs[8], songs[9],
        songs[10], songs[11], songs[12]
    )
)

/** Global list of current playlists */
val playlists = mutableListOf(sweetPiano, funkyTracks)

class UndefinedPlaylistException: Exception()

/** Look for a playlist in playlists global variable
 * @param playlistName the playlist name
 * @return if the playlist exists
*/
fun playlistExist(playlistName: String): Boolean {
    for (playlist in playlists) {
        if (playlist.name.equals(playlistName)) {
            return true
        }
    }
    return false
}

/** Return playlist named playlistName
 * @throws UndefinedPlaylistException if there is no playlist named playlistName
 * in playlists global variable
 */
fun getPlaylist(playlistName: String): Playlist {
    for (playlist in playlists) {
        if (playlist.name.equals(playlistName)) {
            return playlist
        }
    }
    throw UndefinedPlaylistException()
}
package com.androidplaylist

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.androidplaylist.data.Playlist
import com.androidplaylist.data.Song
import com.androidplaylist.data.getPlaylist
import com.androidplaylist.data.playlists

/** Playlist Viewer main component.
 * Contains a title and a list of playlist cards
 * */
@Composable
fun PlaylistsViewer(modifier: Modifier = Modifier) {
    Column (modifier) {

        StateTitle(R.string.playlist_viewer_title, Modifier.align(Alignment.CenterHorizontally))

        // Playlists
        PlaylistCardList()

    }
}

/** A list of PlaylistCard component */
@Composable
private fun PlaylistCardList(modifier: Modifier = Modifier) {

    LazyColumn {
        items(playlists) {
            PlaylistCard(playlist = it)
        }
    }
}

/** A card of a playlist.
 * Contains a title, a cover, the playlist duration and the number of songs
 * @param playlist object that contains playlist data
 */
@Composable
private fun PlaylistCard(playlist: Playlist, modifier: Modifier = Modifier) {

    var expanded by remember { mutableStateOf(false) }

    Card(modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
        .animateContentSize(spring(Spring.DampingRatioNoBouncy, Spring.StiffnessMedium)))
    {
            Row(modifier) {

                Image(
                    painter = painterResource(playlist.cover),
                    contentDescription = playlist.name
                )

                Spacer(modifier.padding(4.dp))

                PlaylistInformations(playlist, modifier)

                Spacer(modifier.weight(1f))

                PlaylistExpandButton(expanded, { expanded = !expanded })
            }

            if (expanded) {
                PlaylistContent(playlist.getSongs())
            }
    }

}

/**
 * Composable that displays information about the playlist :
 * its title, its number of songs and its duration
 * @param playlist contains the playlist data
 */
@Composable
private fun PlaylistInformations(playlist: Playlist, modifier: Modifier = Modifier) {
    Column {
        // Playlist title
        Text(playlist.name)

        Row {
            // Number of songs
            Text(playlist.getStringSongNumber())

            Spacer(modifier.padding(4.dp))

            // Playlist duration
            Text(playlist.getStringDuration())
        }
    }
}

/** The expand button of a playlist card
 * When pressed, it displays or hide the playlist content
 * @param expanded boolean value telling if playlist card is expanded
 * @param onClick function called when the button is pressed
 * */
@Composable
private fun PlaylistExpandButton(expanded: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick) {

        val iconButton = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore

        Icon(
            imageVector = iconButton,
            contentDescription = stringResource(R.string.extend_button_content_description)
        )
    }
}

/** A column of SongCard components
 * @param songs contains songs data
 * */
@Composable
private fun PlaylistContent(songs: List<Song>, modifier: Modifier = Modifier) {
    Column {
        for (i in songs.indices) {
            SongCard(songs[i], i+1)
        }
    }
}

/** A card that displays information about a song in a playlist :
 * its index in the playlist, its name, its duration and its author
 * @param song contains song data
 * @param songIndex the song index in the playlist
 * */
@Composable
private fun SongCard(song: Song, songIndex: Int, modifier: Modifier = Modifier) {
    Row (modifier.padding(8.dp)) {
        Text(songIndex.toString())
        Spacer(Modifier.padding(4.dp))
        Column {
            Text(song.name)
            Text(song.artist)
        }
        Spacer(Modifier.weight(1f))
        Text(song.getStringDuration())
    }
}
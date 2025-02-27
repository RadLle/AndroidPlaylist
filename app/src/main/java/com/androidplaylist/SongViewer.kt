package com.androidplaylist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.androidplaylist.data.Playlist
import com.androidplaylist.data.Song
import com.androidplaylist.data.playlists
import com.androidplaylist.data.songs

/** Song Viewer main component.
 * Contains a title and a list of song cards
 */
@Composable
fun SongViewer(modifier: Modifier = Modifier) {
    Column (modifier) {

        StateTitle(R.string.song_viewer_title, Modifier.align(Alignment.CenterHorizontally))

        // Songs
        SongCardList()

    }
}

/** A list of SongCard components. */
@Composable
private fun SongCardList(modifier: Modifier = Modifier) {
    LazyColumn {
        items(songs) {
            SongCard(song = it)
        }
    }
}

/** A card that displays information about a song :
 * its name, its author and its duration
 * @param song contains */
@Composable
private fun SongCard(song: Song, modifier: Modifier = Modifier) {
    Card (modifier.fillMaxWidth().padding(8.dp)) {
        Row (modifier.padding(8.dp)) {
            Column {
                Text(song.name)
                Text(song.artist)
            }
            Spacer(Modifier.weight(1f))
            Text(song.getStringDuration())
        }
    }

}
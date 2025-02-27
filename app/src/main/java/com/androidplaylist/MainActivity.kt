package com.androidplaylist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
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
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AccessAlarm
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Egg
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlaylistAddCircle
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.androidplaylist.data.Playlist
import com.androidplaylist.data.Song
import com.androidplaylist.data.playlists
import com.androidplaylist.ui.theme.AndroidPlaylistTheme

enum class State {
    PLAYLISTS_VIEWER, SONG_VIEWER, SONG_ADDER, PLAYLIST_MODIFIER
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidPlaylistTheme {
                AndroidPlaylistApp()
            }
        }
    }
}

/** The main composable */
@Composable
fun AndroidPlaylistApp() {
    var appState by remember { mutableStateOf(State.PLAYLISTS_VIEWER) }

    Column (Modifier.animateContentSize(spring(Spring.DampingRatioNoBouncy, Spring.StiffnessLow))) {

        TopButtons(
            switchToPlaylistViewer =  { appState = State.PLAYLISTS_VIEWER },
            switchToSongViewer =  { appState = State.SONG_VIEWER },
            switchToPlaylistAdder =  { appState = State.PLAYLIST_MODIFIER },
            switchToSongAdder =  { appState = State.SONG_ADDER },
            modifier = Modifier.padding(top = 32.dp)
        )

        when (appState) {
            State.PLAYLISTS_VIEWER -> PlaylistsViewer(Modifier.weight(9f))
            State.SONG_VIEWER -> SongViewer(Modifier.weight(9f))
            State.SONG_ADDER -> SongAdder()
            else -> PlaylistModifier()
        }

    }

}

/** A button of the top buttons row, used to change app state */
@Composable
fun TopButton(onClick: () -> Unit, imageVector: ImageVector, modifier: Modifier = Modifier) {
    Card (modifier) {
        IconButton(onClick, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Icon(
                imageVector = imageVector,
                contentDescription = null
            )
        }
    }

}

/** A row of TopButton composables, one button for each state of the app */
@Composable
fun TopButtons(
    switchToPlaylistViewer: () -> Unit,
    switchToSongViewer: () -> Unit,
    switchToSongAdder: () -> Unit,
    switchToPlaylistAdder: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        TopButton(switchToPlaylistViewer, Icons.Filled.PlayArrow, Modifier.weight(1f).padding(4.dp))
        TopButton(switchToSongViewer, Icons.Filled.MusicNote, Modifier.weight(1f).padding(4.dp))
        TopButton(switchToSongAdder, Icons.Filled.Add, Modifier.weight(1f).padding(4.dp))
        TopButton(switchToPlaylistAdder, Icons.Filled.PlaylistAddCircle, Modifier.weight(1f).padding(4.dp))
    }
}

/** Title of a state
 * This component is used by every state composable
 */
@Composable
fun StateTitle(@StringRes titleId: Int, modifier: Modifier = Modifier) {
    Text (
        text = stringResource(titleId),
        fontSize = 50.sp,
        lineHeight = 60.sp,
        textAlign = TextAlign.Center,
        modifier = modifier
            .padding(horizontal = 0.dp, vertical = 16.dp)
    )
}

@Preview
@Composable
fun AndroidPlaylistAppPreview() {

    AndroidPlaylistTheme {
        AndroidPlaylistApp()
    }
}

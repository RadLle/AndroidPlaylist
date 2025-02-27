package com.androidplaylist

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.androidplaylist.data.UndefinedSongException
import com.androidplaylist.data.Playlist
import com.androidplaylist.data.UndefinedPlaylistException
import com.androidplaylist.data.getPlaylist
import com.androidplaylist.data.getSong
import com.androidplaylist.data.playlistExist
import com.androidplaylist.data.playlists

/** Playlist modifier main component.
 * Contains a playlist adder component and a playlist song adder component
 * */
@Composable
fun PlaylistModifier(modifier: Modifier = Modifier) {

    Column {
        PlaylistAdder(modifier)

        PlaylistSongAdder(modifier)
    }

}

/** Playlist adder main component. */
@Composable
private fun PlaylistAdder(modifier: Modifier = Modifier) {

    var playlistName by remember { mutableStateOf("") }
    var confirmationMessage by remember { mutableStateOf("") }

    val successMessage = stringResource(R.string.playlist_adder_success_message)
    val emptyNameMessage = stringResource(R.string.playlist_adder_empty_name_message)
    val playlistAlreadyExistMessage = stringResource(R.string.playlist_adder_already_exist_message)

    /** Function called by the add playlist button.
     * Add a Playlist instance in playlists list if the arguments are correct,
     * and modify confirmationMessage variable */
    fun addSongButtonFunction() {
        if (!playlistName.isEmpty()) {
            if (playlistExist(playlistName)) {
                confirmationMessage = "$playlistAlreadyExistMessage $playlistName"
                return
            }
            playlists.add(Playlist(playlistName, R.drawable.ic_launcher_foreground, mutableListOf()))
            confirmationMessage = successMessage
        } else {
            if (playlistName.isEmpty()) confirmationMessage = emptyNameMessage
        }
    }


    Column {
        StateTitle(R.string.playlist_adder_title, Modifier.align(Alignment.CenterHorizontally))

        // Field to enter a new playlist name
        PlaylistAdderField(playlistName, { playlistName = it }, R.string.playlist_adder_name_field)

        // Button to add a new playlist
        AddButton(
            textId = R.string.playlist_adder_button_text,
            onClick = { addSongButtonFunction() },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
        )

        // Confirmation message
        ConfirmationMessage(confirmationMessage, Modifier.align(Alignment.CenterHorizontally))
    }
}

/** Playlist song adder main component. */
@Composable
private fun PlaylistSongAdder(modifier: Modifier = Modifier) {

    var songName by remember { mutableStateOf("") }
    var playlistName by remember { mutableStateOf("") }
    var confirmationMessage by remember { mutableStateOf("") }

    val successMessage = stringResource(R.string.playlist_song_adder_success_message)
    val undefinedPlaylistMessage = stringResource(R.string.playlist_adder_undefined_playlist_error)
    val undefinedSongMessage = stringResource(R.string.playlist_adder_undefined_song_error)
    val emptyPlaylistMessage = stringResource(R.string.playlist_adder_empty_name_message)
    val emptySongMessage = stringResource(R.string.song_adder_empty_name_message)

    /** Function called by the add song in playlist button.
     * Add a Song instance in songs field of the playlist playlistName if the arguments are correct,
     * and modify confirmationMessage variable */
    fun addPlaylistSongButtonFunction() {
        if(!songName.isEmpty() && !playlistName.isEmpty()) {

            try {
                val playlist = getPlaylist(playlistName)
                val song = getSong(songName)
                playlist.addSong(song)
                confirmationMessage = successMessage

            } catch (exception: UndefinedPlaylistException) {
                confirmationMessage = "$undefinedPlaylistMessage $playlistName"
                return
            } catch (exception: UndefinedSongException) {
                confirmationMessage = "$undefinedSongMessage $songName"
                return
            }
        } else {
            if (playlistName.isEmpty()) confirmationMessage = emptyPlaylistMessage
            if (songName.isEmpty()) confirmationMessage = emptySongMessage
        }
    }

    Column {
        // State title
        StateTitle(R.string.playlist_song_adder_title, Modifier.align(Alignment.CenterHorizontally))

        // Song name text field
        PlaylistAdderField(songName, { songName = it }, R.string.song_adder_button_text)

        // Playlist name text field
        PlaylistAdderField(playlistName, { playlistName = it }, R.string.playlist_adder_button_text)

        // Button to add a song into a playlist
        AddButton(
            R.string.playlist_song_adder_button_text,
            { addPlaylistSongButtonFunction() },
            Modifier.fillMaxWidth().padding(horizontal = 32.dp)
        )

        ConfirmationMessage(confirmationMessage, Modifier.align(Alignment.CenterHorizontally))
    }

}

/** TextField to enter playlist name or song name
 * @param value displayed string inside the text field
 * @param onValueChange update string value function
 * @param labelString string res of the field label */
@Composable
private fun PlaylistAdderField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelString: Int,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(labelString)) },
        singleLine = true,
        modifier = modifier.fillMaxWidth().padding(8.dp)
    )
}

/** Button to add a song into a playlist
 * @param textId string res of the button text
 * @param onClick function called when the button is pressed
 * */
@Composable
private fun AddButton(@StringRes textId: Int, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick, modifier) {
        Text(stringResource(textId))
    }
}

/** Confirmation message text component */
@Composable
private fun ConfirmationMessage(message: String, modifier: Modifier = Modifier) {
    Text(message, modifier)
}

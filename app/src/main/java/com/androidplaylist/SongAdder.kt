package com.androidplaylist

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.androidplaylist.data.Song
import com.androidplaylist.data.songExist
import com.androidplaylist.data.songs

/** Song adder main component. */
@Composable
fun SongAdder(modifier: Modifier = Modifier) {

    var songName by remember { mutableStateOf("") }
    var songArtist by remember { mutableStateOf("") }
    var songDuration by remember { mutableStateOf("") }
    var confirmationMessage by remember { mutableStateOf("") }

    // Strings used by addSongButtonFunction
    val successMessage = stringResource(R.string.song_adder_success_message)
    val emptyNameMessage = stringResource(R.string.song_adder_empty_name_message)
    val emptyArtistMessage = stringResource(R.string.song_adder_empty_artist_message)
    val emptyDurationMessage = stringResource(R.string.song_adder_empty_duration_message)
    val numberFormatMessage = stringResource(R.string.song_adder_number_format_message)
    val songAlreadyExistMessage = stringResource(R.string.song_adder_already_exist_message)

    /** Function called by the add song button
     * Add a Song instance in songs list if the arguments are correct,
     * and update confirmationMessage */
    fun addSongButtonFunction() {
        if (songName.isNotEmpty() && songArtist.isNotEmpty() && songDuration.isNotEmpty()) {
            if (songExist(songName)) {
                confirmationMessage = "$songAlreadyExistMessage $songName"
                return
            }
            try {
                songs.add(Song(songName, songDuration.toInt(), songArtist));
                confirmationMessage = successMessage
            } catch (exception: NumberFormatException) {
                confirmationMessage = numberFormatMessage
                return
            }
        } else {
            if (songDuration.isEmpty()) confirmationMessage = emptyDurationMessage
            if (songArtist.isEmpty()) confirmationMessage = emptyArtistMessage
            if (songName.isEmpty()) confirmationMessage = emptyNameMessage
        }
    }


    Column {
        StateTitle(R.string.song_adder_title, Modifier.align(Alignment.CenterHorizontally))

        SongAdderField(songName, { songName = it }, R.string.song_adder_name_field, KeyboardType.Text)

        SongAdderField(songArtist, { songArtist = it }, R.string.song_adder_author_field, KeyboardType.Text)

        SongAdderField(songDuration, { songDuration = it }, R.string.song_adder_duration_field, KeyboardType.Number)

        AddSongButton(
            onClick = { addSongButtonFunction() },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
        )

        AddedConfirmationMessage(confirmationMessage, Modifier.align(Alignment.CenterHorizontally))
    }
}

/** TextField to enter playlist name or song name
 * @param value displayed string inside the text field
 * @param onValueChange update string value function
 * @param labelString string res of the field label
 * @param keyboardType to change android's keyboard for the field
 * */
@Composable
private fun SongAdderField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelString: Int,
    keyboardType: KeyboardType,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(labelString)) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = modifier.fillMaxWidth().padding(8.dp)
    )
}

/** Button to add a song in songs list */
@Composable
private fun AddSongButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick, modifier) {
        Text(stringResource(R.string.song_adder_button_text))
    }
}

/** Confirmation message text component */
@Composable
private fun AddedConfirmationMessage(message: String, modifier: Modifier = Modifier) {
    Text(message, modifier)
}
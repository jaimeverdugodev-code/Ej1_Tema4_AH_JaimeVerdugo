package com.example.ej1_tema4_pmdp_jaimeverdugoserrano

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView
import coil.compose.rememberAsyncImagePainter

@Composable
fun MainScreen(
    onPlaySound: () -> Unit,
    onPlayRemoteSound: () -> Unit,
    onPauseRemoteSound: () -> Unit,
    onPlayRemoteVideo: () -> Unit,
    onPauseRemoteVideo: () -> Unit,
    onOpenCamera: () -> Unit,
    capturedImageUri: Uri?,
    videoPlayer: Player?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()), // Para que quepa todo el contenido
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cámara
        Button(onClick = { onOpenCamera() }) {
            Text("Abrir Cámara")
        }
        Spacer(modifier = Modifier.height(16.dp))
        capturedImageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = "Imagen Capturada",
                modifier = Modifier.size(150.dp)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        // Video
        VideoPlayer(
            player = videoPlayer,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(onClick = { onPlayRemoteVideo() }) {
                Text("Reproducir Video")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { onPauseRemoteVideo() }) {
                Text("Pausar Video")
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        // Audio Largo
        Row {
            Button(onClick = { onPlayRemoteSound() }) {
                Text("Reproducir Audio Largo")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { onPauseRemoteSound() }) {
                Text("Pausar Audio Largo")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Audio Corto
        Button(onClick = { onPlaySound() }) {
            Text("Reproducir Sonido Corto")
        }
    }
}

@Composable
fun VideoPlayer(player: Player?, modifier: Modifier = Modifier) {
    AndroidView(
        factory = {
            PlayerView(it).apply {
                useController = true
            }
        },
        update = { playerView ->
            playerView.player = player
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(
        onPlaySound = {},
        onPlayRemoteSound = {},
        onPauseRemoteSound = {},
        onPlayRemoteVideo = {},
        onPauseRemoteVideo = {},
        onOpenCamera = {},
        capturedImageUri = null,
        videoPlayer = null
    )
}

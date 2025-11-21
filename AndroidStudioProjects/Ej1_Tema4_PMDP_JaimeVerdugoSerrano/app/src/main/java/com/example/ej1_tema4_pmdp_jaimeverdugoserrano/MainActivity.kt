package com.example.ej1_tema4_pmdp_jaimeverdugoserrano

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.SoundPool
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.ej1_tema4_pmdp_jaimeverdugoserrano.ui.theme.Ej1_Tema4_PMDP_JaimeVerdugoSerranoTheme
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

class MainActivity : ComponentActivity() {

    private lateinit var soundPool: SoundPool
    private var soundId: Int = 0
    private var isSoundPoolLoaded = false
    private var audioPlayer: ExoPlayer? = null
    private var videoPlayer: ExoPlayer? = null

    // State for the camera captured image URI
    private var capturedImageUri by mutableStateOf<Uri?>(null)

    // Temporary URI to hold the value while the camera app is open
    private var tempImageUri: Uri? = null

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            capturedImageUri = tempImageUri
        }
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Permission granted, launch camera
            openCamera()
        }
        // Aquí podrías mostrar un mensaje si el usuario deniega el permiso
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- SoundPool, ExoPlayer setup... ---
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA) // Cambiado a MEDIA
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC) // Cambiado a MUSIC
            .build()
        soundPool = SoundPool.Builder().setMaxStreams(1).setAudioAttributes(audioAttributes).build()

        soundPool.setOnLoadCompleteListener { _, sampleId, status ->
            Log.d("SoundPool", "Sound loaded. Sample ID: $sampleId, Status: $status")
            if (status == 0) {
                isSoundPoolLoaded = true
                Log.d("SoundPool", "isSoundPoolLoaded set to true")
            } else {
                Log.e("SoundPool", "Error loading sound file. Check if 'res/raw/sonido' exists and is a valid audio file.")
            }
        }

        soundId = soundPool.load(this, R.raw.sonido, 1)
        audioPlayer = ExoPlayer.Builder(this).build()
        videoPlayer = ExoPlayer.Builder(this).build()

        setContent {
            Ej1_Tema4_PMDP_JaimeVerdugoSerranoTheme {
                MainScreen(
                    onPlaySound = { playSound() },
                    onPlayRemoteSound = { playRemoteAudio() },
                    onPauseRemoteSound = { pauseRemoteAudio() },
                    onPlayRemoteVideo = { playRemoteVideo() },
                    onPauseRemoteVideo = { pauseRemoteVideo() },
                    onOpenCamera = { openCamera() },
                    capturedImageUri = capturedImageUri, // Pasa el estado directamente
                    videoPlayer = videoPlayer
                )
            }
        }
    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            val newImageUri = createImageUri()
            if (newImageUri != null) {
                tempImageUri = newImageUri
                takePictureLauncher.launch(newImageUri)
            }
            // Opcional: Podrías mostrar un mensaje si la URI es nula
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun createImageUri(): Uri? {
        // Para Android 10 (Q) y superior, usa MediaStore para guardar en la galería pública.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", java.util.Locale.getDefault()).format(Date())
            val imageFileName = "JPEG_${timeStamp}"

            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "$imageFileName.jpg")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }

            return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        } else {
            // Para versiones anteriores, usa el método de FileProvider (guarda en el directorio privado de la app).
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", java.util.Locale.getDefault()).format(Date())
            val imageFileName = "JPEG_" + timeStamp + "_"

            val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

            val imageFile = File.createTempFile(
                imageFileName, /* prefijo */
                ".jpg",       /* sufijo */
                storageDir    /* directorio */
            )

            return FileProvider.getUriForFile(
                Objects.requireNonNull(this),
                "com.example.ej1_tema4_pmdp_jaimeverdugoserrano.provider",
                imageFile
            )
        }
    }

    private fun playSound() {
        Log.d("SoundPool", "playSound() called. isSoundPoolLoaded: $isSoundPoolLoaded")
        if (isSoundPoolLoaded) {
            val streamId = soundPool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f)
            if (streamId == 0) {
                Log.e("SoundPool", "play() failed! The sound failed to play. Check audio file format.")
            } else {
                Log.d("SoundPool", "soundPool.play() successful. Stream ID: $streamId")
            }
        } else {
            Log.w("SoundPool", "playSound() called but sound is not loaded yet.")
        }
    }

    private fun playRemoteAudio() {
        val mediaItem = MediaItem.fromUri("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")
        audioPlayer?.setMediaItem(mediaItem)
        audioPlayer?.prepare()
        audioPlayer?.play()
    }

    private fun pauseRemoteAudio() {
        audioPlayer?.pause()
    }

    private fun playRemoteVideo() {
        val mediaItem = MediaItem.fromUri("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
        videoPlayer?.setMediaItem(mediaItem)
        videoPlayer?.prepare()
        videoPlayer?.play()
    }

    private fun pauseRemoteVideo() {
        videoPlayer?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
        audioPlayer?.release()
        videoPlayer?.release()
    }
}

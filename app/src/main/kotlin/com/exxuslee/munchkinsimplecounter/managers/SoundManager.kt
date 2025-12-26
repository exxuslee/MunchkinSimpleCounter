package com.exxuslee.munchkinsimplecounter.managers

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

class SoundManager(context: Context) {

    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private val toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 80)

    fun play(sound: ClickSound) {
        if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) <= 0) return
        runCatching {
            toneGenerator.startTone(sound.tone, sound.durationMs)
        }
    }

    fun release() {
        runCatching { toneGenerator.release() }
    }
}

enum class ClickSound(
    val tone: Int,
    val durationMs: Int = 100
) {
    ADD_LEVEL(ToneGenerator.TONE_DTMF_1),
    ADD_BONUS(ToneGenerator.TONE_DTMF_B),
    SUB_LEVEL(ToneGenerator.TONE_PROP_NACK),
    SUB_BONUS(ToneGenerator.TONE_SUP_BUSY)
}

@Composable
fun rememberSoundManager(): SoundManager {
    val context = LocalContext.current
    return remember {
        SoundManager(context)
    }.also { manager ->
        DisposableEffect(Unit) {
            onDispose {
                manager.release()
            }
        }
    }
}
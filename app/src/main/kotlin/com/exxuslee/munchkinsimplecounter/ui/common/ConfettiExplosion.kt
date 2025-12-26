package com.exxuslee.munchkinsimplecounter.ui.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import kotlin.random.Random

@Composable
fun ArcCornerConfetti(
    trigger: Boolean,
    modifier: Modifier = Modifier,
    particlesPerSide: Int = 60
) {
    if (!trigger) return

    val particles = remember {
        buildList {
            repeat(particlesPerSide) {
                add(ArcConfettiParticle(fromLeft = true))
                add(ArcConfettiParticle(fromLeft = false))
            }
        }
    }

    val transition = rememberInfiniteTransition(label = "confetti")

    val time by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "time"
    )

    Canvas(modifier = modifier.fillMaxSize()) {
        particles.forEach { p ->
            val t = (time + p.delay) % 1f

            val startX = if (p.fromLeft) 0f else size.width
            val startY = size.height / 4f

            val x = startX + p.velocityX * t * size.width
            val y = startY + (p.initialVelocityY * t * size.height) + (p.gravity * t * t)

            drawRect(
                color = p.color,
                topLeft = Offset(x, y),
                size = Size(p.size, p.size)
            )
        }
    }
}

data class ArcConfettiParticle(
    val fromLeft: Boolean,

    val color: Color = listOf(
        Color.Red, Color.Yellow, Color.Green,
        Color.Cyan, Color.Magenta
    ).random(),

    val velocityX: Float = if (fromLeft) Random.nextFloat() * 0.5f + 0.1f
    else -(Random.nextFloat() * 0.5f + 0.1f),

    // Стартовый пинок вверх
    val initialVelocityY: Float = -(Random.nextFloat() * 0.2f + 0.2f),

    val gravity: Float = Random.nextFloat() * 500f + 100f,
    val size: Float = Random.nextInt(6, 14).toFloat(),
    val delay: Float = Random.nextFloat()
)
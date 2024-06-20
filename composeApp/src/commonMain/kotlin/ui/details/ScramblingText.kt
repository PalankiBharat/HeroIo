import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

@Composable
fun ScramblingText(
    modifier: Modifier = Modifier,
    data: List<String>,
    delayMillis: Long = 1000,
    pauseMillis: Long = 3000 // Pause after each word change
) {
    var displayedText by remember { mutableStateOf(data.first()) }
    val scope = rememberCoroutineScope()
    val chars = "!<>-_/[]{}—=+*^?#_____________"
    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = data) {
        while (true) {
            val targetText = data[currentIndex]
            scope.launch {
                for (frame in generateScrambledFrames(displayedText, targetText, chars)) {
                    displayedText = frame
                    delay(60L) // Control the speed of scrambling
                }
                displayedText = targetText
            }
            delay(pauseMillis) // Pause after completing the word change
            currentIndex = (currentIndex + 1) % data.size
        }
    }
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        BasicText(
            text = displayedText,
            style = TextStyle(fontSize = 32.sp, color = Color.White, fontWeight = FontWeight.Bold)
        )
    }
}

fun generateScrambledFrames(
    oldText: String,
    newText: String,
    chars: String,
    totalFrames: Int = 15
): List<String> {
    val frames = mutableListOf<String>()
    val maxLength = max(oldText.length, newText.length)
    val minLength = min(oldText.length, newText.length)
    val diff = maxLength - minLength

    for (frame in 0 until totalFrames) {
        val percentage = frame / totalFrames.toFloat()
        val currentLength = if (oldText.length > newText.length) {
            maxLength - (diff * percentage).toInt()
        } else {
            minLength + (diff * percentage).toInt()
        }

        val scrambled = CharArray(currentLength) { ' ' }
        for (i in 0 until currentLength) {
            val fromChar = oldText.getOrNull(i) ?: ' '
            val toChar = newText.getOrNull(i) ?: ' '

            scrambled[i] = when {
                frame >= totalFrames -> toChar
                frame < totalFrames / 2 -> if (Random.nextFloat() < 0.5f) chars.random() else fromChar
                else -> if (Random.nextFloat() < percentage) toChar else chars.random()
            }
        }
        frames.add(scrambled.concatToString())
    }
    frames.add(newText)
    return frames
}

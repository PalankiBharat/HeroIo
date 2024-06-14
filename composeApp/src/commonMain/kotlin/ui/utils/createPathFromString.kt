import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path

fun createPathFromString(data: String, maxWidth: Float, maxHeight: Float): Path {
    val path = Path()
    val commands = data.split(" ")

    // Determine the max dimensions from the data
    var actualMaxX = 0f
    var actualMaxY = 0f

    // First pass to determine the actual max dimensions
    var i = 0
    while (i < commands.size) {
        when (commands[i]) {
            "M", "L" -> {
                val x = commands[i + 1].toFloat()
                val y = commands[i + 2].toFloat()
                if (x > actualMaxX) actualMaxX = x
                if (y > actualMaxY) actualMaxY = y
                i += 3
            }
            "H" -> {
                val x = commands[i + 1].toFloat()
                if (x > actualMaxX) actualMaxX = x
                i += 2
            }
            "V" -> {
                val y = commands[i + 1].toFloat()
                if (y > actualMaxY) actualMaxY = y
                i += 2
            }
            else -> i++
        }
    }

    // Second pass to create the path with normalized coordinates
    i = 0
    var currentPoint = Offset.Zero
    while (i < commands.size) {
        when (commands[i]) {
            "M" -> {
                val x = commands[i + 1].toFloat() / actualMaxX * maxWidth
                val y = commands[i + 2].toFloat() / actualMaxY * maxHeight
                path.moveTo(x, y)
                currentPoint = Offset(x, y)
                i += 3
            }
            "H" -> {
                val x = commands[i + 1].toFloat() / actualMaxX * maxWidth
                path.lineTo(x, currentPoint.y)
                currentPoint = Offset(x, currentPoint.y)
                i += 2
            }
            "V" -> {
                val y = commands[i + 1].toFloat() / actualMaxY * maxHeight
                path.lineTo(currentPoint.x, y)
                currentPoint = Offset(currentPoint.x, y)
                i += 2
            }
            "L" -> {
                val x = commands[i + 1].toFloat() / actualMaxX * maxWidth
                val y = commands[i + 2].toFloat() / actualMaxY * maxHeight
                path.lineTo(x, y)
                currentPoint = Offset(x, y)
                i += 3
            }
            "Z" -> {
                path.close()
                i += 1
            }
            else -> i++
        }
    }

    return path
}

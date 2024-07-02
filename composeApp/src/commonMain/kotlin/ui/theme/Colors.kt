package ui.theme

import androidx.compose.ui.graphics.Color

val appBG = Color.Black
val themeColor = Color(0xFF00FFB2)
val yellow = Color(0xFFE8ED03)
val green = Color(0xFF00FFF0)
val red = Color(0xFFD80606)
val orange = Color(0xFFFF5333)
val purple = Color(0xFFAC31E6)
val blue = Color(0xFF0057F4)

fun Color.toHSL(): FloatArray {
    val r = red
    val g = green
    val b = blue

    val max = maxOf(r, g, b)
    val min = minOf(r, g, b)
    val delta = max - min

    val l = (max + min) / 2f
    var h = 0f
    var s = 0f

    if (delta != 0f) {
        s = if (l < 0.5f) delta / (max + min) else delta / (2f - max - min)
        h = when (max) {
            r -> (g - b) / delta + (if (g < b) 6f else 0f)
            g -> (b - r) / delta + 2f
            b -> (r - g) / delta + 4f
            else -> h
        }
        h /= 6f
    }

    return floatArrayOf(h * 360f, s, l)
}

fun Color.fromHSL(hsl: FloatArray): Color {
    val h = hsl[0] / 360f
    val s = hsl[1]
    val l = hsl[2]

    val q = if (l < 0.5f) l * (1f + s) else l + s - l * s
    val p = 2f * l - q

    fun hueToRgb(p: Float, q: Float, t: Float): Float {
        var tVar = t
        if (tVar < 0f) tVar += 1f
        if (tVar > 1f) tVar -= 1f
        return when {
            tVar < 1f / 6f -> p + (q - p) * 6f * tVar
            tVar < 1f / 2f -> q
            tVar < 2f / 3f -> p + (q - p) * (2f / 3f - tVar) * 6f
            else -> p
        }
    }

    val r = hueToRgb(p, q, h + 1f / 3f)
    val g = hueToRgb(p, q, h)
    val b = hueToRgb(p, q, h - 1f / 3f)

    return Color(r, g, b, alpha)
}

fun Color.brighten(factor: Float): Color {
    val hsl = this.toHSL()
    // Increase lightness
    hsl[2] = (hsl[2] + factor).coerceIn(0f, 1f)
    return this.fromHSL(hsl)
}

package com.easit.profileimage.model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Shader
import android.graphics.Typeface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun createSeed(): Int{
    return (100000..999999).random()
}

fun fullImageProcess(
                    text: String,
                    width: Int,
                    height: Int,
                    startColor: Color,
                    endColor: Color,
                    textColor: Color,
                    textSize: Float,
                    typeface: Typeface? = null,
                    shuffleSeed: Long
): Bitmap{
    val response = setBackground(width, height, startColor, endColor)
    val encoder = ImageEncoder()
    val design = encoder.reassembleImage(8, encoder.shuffleBitmap(encoder.splitImageToParts(8, response), shuffleSeed))
    return setTextOnBackground(design, text, width, height, textColor, textSize, typeface)
}

fun fullImageProcess2(
                    text: String,
                    width: Int,
                    height: Int,
                    startColor: Color,
                    endColor: Color,
                    textColor: Color,
                    textSize: Float,
                    typeface: Typeface? = null,
                    shuffleSeed: Long
): Bitmap{
    val response = setBackground(width, height, startColor, endColor)
    val encoder = ImageEncoder()
    val shuffled = encoder.shuffleBitmap(encoder.splitImageToParts(8, response), shuffleSeed)

    val result = mutableListOf<Bitmap>()
    for (i in shuffled){
        result.add(setTextOnBackground(i, text, width/8, height/8, textColor, textSize, typeface))
    }
    return encoder.reassembleImage(8, result)

}

fun fullImageProcess3(
                    text: String,
                    width: Int,
                    height: Int,
                    startColor: Color,
                    endColor: Color,
                    textColor: Color,
                    textSize: Float,
                    typeface: Typeface? = null,
                    shuffleSeed: Long
): Bitmap{
    val response = setBackground(width, height, startColor, endColor)
    val encoder = ImageEncoder()
    val shuffled = encoder.shuffleBitmap(encoder.splitImageToParts(6, response), shuffleSeed)

    val result = mutableListOf<Bitmap>()
    shuffled.forEachIndexed { index, bitmap ->
        //result.add(setTextOnBackground(bitmap, text[index].toString(), width/6, height/6, textColor, textSize, typeface))
    }
    return encoder.reassembleImage(6, result)

}

fun setTextOnBackground(
    background: Bitmap,
    text: String,
    width: Int,
    height: Int,
    textColor: Color,
    textSize: Float,
    typeface: Typeface? = null
) : Bitmap {
    val bitmap = Bitmap.createBitmap(background, 0, 0, width, height)
    val canvas = Canvas(bitmap)
    val paint = Paint().apply {
        this.color = textColor.toArgb()
        this.textSize = textSize
        this.typeface = typeface ?: Typeface.DEFAULT
    }

    val bounds = Rect()
    paint.getTextBounds(text, 0, text.length, bounds)

    val x = (width - bounds.width()) / 2
    val y = (height + bounds.height()) / 2

    canvas.drawText(text, x.toFloat(), y.toFloat(), paint)

    return bitmap
}

fun setBackground(width: Int, height: Int, startColor: Color, endColor: Color) : Bitmap{
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    val gradient = LinearGradient(
        0f, 0f, width.toFloat(), height.toFloat(),
        intArrayOf(startColor.toArgb(), endColor.toArgb()), null, Shader.TileMode.CLAMP
    )

    val paint = Paint().apply {
        this.shader = gradient
    }

    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    return bitmap
}

fun getProfileById(profileId: String): Bitmap{
    val initials = "${profileId[0]}${profileId[1]}"

    val startRed = "${profileId[2]}${profileId[3]}${profileId[4]}".toInt()
    val startGreen = "${profileId[5]}${profileId[6]}${profileId[7]}".toInt()
    val startBlue = "${profileId[8]}${profileId[9]}${profileId[10]}".toInt()

    val endRed = "${profileId[11]}${profileId[12]}${profileId[13]}".toInt()
    val endGreen = "${profileId[14]}${profileId[15]}${profileId[16]}".toInt()
    val endBlue = "${profileId[17]}${profileId[18]}${profileId[19]}".toInt()

    val backgroundShuffleSeed = ("${profileId[20]}${profileId[21]}${profileId[22]}${profileId[23]}${profileId[24]}" +
            "${profileId[25]}").toLong()
    val startColor = Color(red = startRed, green = startGreen, blue = startBlue)
    val endColor = Color(red = endRed, green = endGreen, blue = endBlue)

    //val k = Color.valueOf(startColorArgb)
    return fullImageProcess(
        text = initials, width = 200, height = 200, startColor = startColor, endColor = endColor,
        textColor = Color.White, textSize = 100f, typeface = Typeface.MONOSPACE, shuffleSeed = backgroundShuffleSeed
    )
}

fun getProfileById2(profileId: String): Bitmap{
    val initials = "${profileId[0]}${profileId[1]}${profileId[2]}"
    val iD = "${profileId[3]}${profileId[4]}${profileId[5]}${profileId[6]}${profileId[7]}${profileId[8]}${profileId[9]}${profileId[10]}${profileId[11]}"

    val startRed = "${profileId[12]}${profileId[13]}${profileId[14]}".toInt()
    val startGreen = "${profileId[15]}${profileId[16]}${profileId[17]}".toInt()
    val startBlue = "${profileId[18]}${profileId[19]}${profileId[20]}".toInt()

    val endRed = "${profileId[21]}${profileId[22]}${profileId[23]}".toInt()
    val endGreen = "${profileId[24]}${profileId[25]}${profileId[26]}".toInt()
    val endBlue = "${profileId[27]}${profileId[28]}${profileId[29]}".toInt()

    val backgroundShuffleSeed = ("${profileId[30]}${profileId[31]}${profileId[32]}${profileId[33]}${profileId[34]}${profileId[35]}").toLong()
    val startColor = Color(red = startRed, green = startGreen, blue = startBlue)
    val endColor = Color(red = endRed, green = endGreen, blue = endBlue)

    //val k = Color.valueOf(startColorArgb)
    return fullImageProcess3(
        text = initials, width = 200, height = 200, startColor = startColor, endColor = endColor,
        textColor = Color.White, textSize = 100f, typeface = Typeface.MONOSPACE, shuffleSeed = backgroundShuffleSeed
    )
}


/*
fun storeColorAsInt(color: Color): Int {
    // Convert Compose Color to Android Color Int using toArgb()
    return color.toArgb()
}

fun retrieveColorFromInt(colorInt: Int): Color {
    // Create Compose Color from Android Color Int
    return Color(colorInt)
}

fun getColorById(id: Int): Color{
    return Color(alpha = id shr 24 and 0xFF, red = id shr 16 and 0xFF, green = id shr 8 and 0xFF, blue = id and 0xFF)
}
fun Int.toColor (){
    val alpha = this shr 24 and 0xFF
    val red = this shr 16 and 0xFF
    val green = this shr 8 and 0xFF
    val blue = this and 0xFF
    return Color.argb(alpha, red, green, blue)
}

fun Int.toColorHex() : Color {
    val hexString = this.toUInt().toString(16).padStart(8, '0')
    return Color.
}

fun getProfileById2(profileId: String): Bitmap{
    val initials = "${profileId[0]}${profileId[1]}"
    val startColorArgb = ("${profileId[2]}${profileId[3]}${profileId[4]}${profileId[5]}${profileId[6]}" +
            "${profileId[7]}${profileId[8]}${profileId[9]}${profileId[10]}${profileId[11]}").toInt()
    val endColorArgb = ("${profileId[12]}${profileId[13]}${profileId[14]}${profileId[15]}${profileId[16]}" +
            "${profileId[17]}${profileId[18]}${profileId[19]}${profileId[20]}${profileId[21]}").toInt()
    val backgroundShuffleSeed = ("${profileId[22]}${profileId[23]}${profileId[24]}" +
            "${profileId[25]}${profileId[26]}${profileId[27]}").toLong()
    val startColor = retrieveColorFromInt(startColorArgb)//Color(startColorArgb)
    val endColor = retrieveColorFromInt(endColorArgb)//Color(endColorArgb)

    val k = Color.valueOf(startColorArgb)
    return fullImageProcess(
        text = initials, width = 200, height = 200, startColor = startColor, endColor = endColor,
        textColor = Color.White, textSize = 100f, typeface = Typeface.MONOSPACE, shuffleSeed = backgroundShuffleSeed
    )
}*/
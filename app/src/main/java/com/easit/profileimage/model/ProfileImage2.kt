package com.easit.profileimage.model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

fun getProfileByIdOld(profileId: String): Bitmap{
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

    return fullImageProcessOld(
        text = initials, width = 1200, height = 1200, startColor = startColor, endColor = endColor,
        textColor = Color.White, textSize = 600f, typeface = Typeface.DEFAULT_BOLD, shuffleSeed = backgroundShuffleSeed
    )
}

fun fullImageProcessOld(
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
    return setTextOnBackgroundOld(design, text, width, height, textColor, textSize, typeface)
}

fun setTextOnBackgroundOld(
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

fun createBitmapWithWhiteBackground(width: Int, height: Int): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    canvas.drawColor(android.graphics.Color.WHITE)
    return bitmap
}
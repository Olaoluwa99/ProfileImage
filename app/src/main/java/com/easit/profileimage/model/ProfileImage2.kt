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

fun fullImageProcessM(
                    text: String,
                    width: Int,
                    height: Int,
                    startColor: Color,
                    endColor: Color,
                    textColor: Color,
                    textSize: Float,
                    typeface: Typeface? = null,
                    shuffleSeed: Int
): Bitmap{
    val response = setBackgroundM(width, height, startColor, endColor)
    val encoder = ImageEncoder()
    val shuffled = encoder.shuffleBitmap(encoder.splitImageToParts(6, response), shuffleSeed.toLong())

    val result = mutableListOf<Bitmap>()
    shuffled.forEachIndexed { index, bitmap ->
        result.add(setTextOnBackgroundM(bitmap, text[index].toString(), width/6, height/6, textColor, textSize, typeface))
    }
    return encoder.reassembleImage(6, result)
}

fun setTextOnBackgroundM(
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

fun setBackgroundM(width: Int, height: Int, startColor: Color, endColor: Color) : Bitmap{
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

fun getProfileByIdM(profileId: String): Bitmap{
    val initials = "${profileId[0]}${profileId[1]}${profileId[2]}"
    val iD = "${profileId[3]}${profileId[4]}${profileId[5]}${profileId[6]}${profileId[7]}${profileId[8]}${profileId[9]}${profileId[10]}${profileId[11]}"

    val startRed = "${profileId[12]}${profileId[13]}${profileId[14]}".toInt()
    val startGreen = "${profileId[15]}${profileId[16]}${profileId[17]}".toInt()
    val startBlue = "${profileId[18]}${profileId[19]}${profileId[20]}".toInt()

    val endRed = "${profileId[21]}${profileId[22]}${profileId[23]}".toInt()
    val endGreen = "${profileId[24]}${profileId[25]}${profileId[26]}".toInt()
    val endBlue = "${profileId[27]}${profileId[28]}${profileId[29]}".toInt()

    val backgroundShuffleSeed = ("${profileId[30]}${profileId[31]}${profileId[32]}${profileId[33]}${profileId[34]}${profileId[35]}").toInt()
    val startColor = Color(red = startRed, green = startGreen, blue = startBlue)
    val endColor = Color(red = endRed, green = endGreen, blue = endBlue)

    //val k = Color.valueOf(startColorArgb)
    return fullImageProcessM(
        text = profileId, width = 1200, height = 1200, startColor = startColor, endColor = endColor,
        textColor = Color.White, textSize = 120f, typeface = Typeface.DEFAULT, shuffleSeed = backgroundShuffleSeed
    )
}

fun getProfileByIdN(profileId: String): Bitmap{
    val initials = "${profileId[0]}${profileId[1]}${profileId[2]}"
    val iD = "${profileId[3]}${profileId[4]}${profileId[5]}${profileId[6]}${profileId[7]}${profileId[8]}${profileId[9]}${profileId[10]}${profileId[11]}"

    val startRed = "${profileId[12]}${profileId[13]}${profileId[14]}".toInt()
    val startGreen = "${profileId[15]}${profileId[16]}${profileId[17]}".toInt()
    val startBlue = "${profileId[18]}${profileId[19]}${profileId[20]}".toInt()

    val endRed = "${profileId[21]}${profileId[22]}${profileId[23]}".toInt()
    val endGreen = "${profileId[24]}${profileId[25]}${profileId[26]}".toInt()
    val endBlue = "${profileId[27]}${profileId[28]}${profileId[29]}".toInt()

    val backgroundShuffleSeed = ("${profileId[30]}${profileId[31]}${profileId[32]}${profileId[33]}${profileId[34]}${profileId[35]}").toInt()
    val startColor = Color(red = startRed, green = startGreen, blue = startBlue)
    val endColor = Color(red = endRed, green = endGreen, blue = endBlue)

    //val k = Color.valueOf(startColorArgb)
    return fullImageProcessStack(
        text = profileId, width = 1200, height = 1200, startColor = startColor, endColor = endColor,
        textColor = Color.White, textSize = 50f, typeface = Typeface.DEFAULT, shuffleSeed = backgroundShuffleSeed.toLong()
    )
}
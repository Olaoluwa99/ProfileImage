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

fun fullImageProcessStack(
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
    //return setTextOnBackground(design, text, width, height, textColor, textSize, typeface)
    return setTextsOnBackgroundStack(design, "EAS", "123456789", "123456789", "123456789", "123456",
        180f, 100f, 100f, 100f, 120f, textColor,
        typeface)
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

fun setTextsOnBackgroundStack(
    background: Bitmap,
    text1: String,
    text2: String,
    text3: String,
    text4: String,
    text5: String,
    textSize1: Float,
    textSize2: Float,
    textSize3: Float,
    textSize4: Float,
    textSize5: Float,
    textColor: Color,
    typeface: Typeface? = null
) : Bitmap {
    val bitmap = Bitmap.createBitmap(background, 0, 0, background.width, background.height)
    val canvas = Canvas(bitmap)

    // Calculate total vertical spacing for texts
    val totalTextHeight = /*textSize1 + */textSize2 + textSize3 + textSize4 + textSize5
    val spacing = totalTextHeight / 8

    // Calculate center point for vertical alignment
    val centerY = bitmap.height / 2f

    // Draw each text with its specific size and position
    var y = centerY - (totalTextHeight + spacing) / 2f // Start drawing from the centered position
    for ((index, text) in listOf(text1, text2, text3, text4, text5).withIndex()) {
        val textSize = when (index + 1) {
            1 -> textSize1
            2 -> textSize2
            3 -> textSize3
            4 -> textSize4
            else -> textSize5
        }

        val paint = Paint().apply {
            this.color = textColor.toArgb()
            this.textSize = textSize
            this.textAlign = Paint.Align.CENTER  // Center horizontally
            if (index == 0){
                this.typeface = typeface ?: Typeface.MONOSPACE
            }else this.typeface = typeface ?: Typeface.SANS_SERIF
        }

        canvas.drawText(text, bitmap.width / 2f, y.toFloat(), paint)
        y += textSize + spacing // Move down for the next text
    }
    return bitmap
}



fun setTextsOnBackgroundStackOld(
    background: Bitmap,
    text1: String,
    text2: String,
    text3: String,
    text4: String,
    text5: String,
    textSize1: Float,
    textSize2: Float,
    textSize3: Float,
    textSize4: Float,
    textSize5: Float,
    textColor: Color,
    typeface: Typeface? = null
) : Bitmap {
    val bitmap = Bitmap.createBitmap(background, 0, 0, background.width, background.height)
    val canvas = Canvas(bitmap)

    // Calculate total vertical spacing for texts
    val totalTextHeight = textSize1 + textSize2 + textSize3 + textSize4 + textSize5
    val spacing = totalTextHeight / 5 // Adjust spacing as needed

    // Draw each text with its specific size and position
    var y = spacing // Start drawing from the top with appropriate spacing
    for ((index, text) in listOf(text1, text2, text3, text4, text5).withIndex()) {
        val textSize = when (index + 1) {
            1 -> textSize1
            2 -> textSize2
            3 -> textSize3
            4 -> textSize4
            else -> textSize5
        }

        val paint = Paint().apply {
            this.color = textColor.toArgb()
            this.textSize = textSize
            this.textAlign = Paint.Align.CENTER
            this.typeface = typeface ?: Typeface.DEFAULT
        }

        canvas.drawText(text, bitmap.width / 2f, y.toFloat(), paint)
        y += textSize + spacing // Move down for the next text
    }

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
fun splitImageToParts(imagePartsRoot: Int, bitmap: Bitmap): List<Bitmap> {
    val imageWidth = bitmap.width
    val imageHeight = bitmap.height

    val tileWidth = imageWidth / imagePartsRoot
    val tileHeight = imageHeight / imagePartsRoot
    val tiles = mutableListOf<Bitmap>()

    // Split image into 16 parts
    for (y in 0 until imagePartsRoot) {
        for (x in 0 until imagePartsRoot) {
            val croppedBitmap = Bitmap.createBitmap(
                bitmap,
                x * tileWidth, y * tileHeight,
                tileWidth, tileHeight
            )
            tiles.add(croppedBitmap)
        }
    }

    return tiles
}
fun splitImageToPartsHorizontal(imagePartsRoot: Int, bitmap: Bitmap): List<Bitmap> {
    val imageWidth = bitmap.width
    val imageHeight = bitmap.height

    val tileHeight = imageHeight / imagePartsRoot
    val tiles = mutableListOf<Bitmap>()

    // Split image into horizontal parts
    for (y in 0 until imagePartsRoot) {
        val croppedBitmap = Bitmap.createBitmap(
            bitmap,
            0, y * tileHeight,
            imageWidth, tileHeight
        )
        tiles.add(croppedBitmap)
    }
    return tiles
}
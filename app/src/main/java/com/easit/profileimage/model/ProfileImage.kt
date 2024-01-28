package com.easit.profileimage.model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.Typeface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

/*
fun getProfileById(profileId: String): Bitmap{
    val initials = "${profileId[0]}${profileId[1]}${profileId[2]}"
    val iD = "${profileId[3]}${profileId[4]}${profileId[5]}${profileId[6]}${profileId[7]}${profileId[8]}${profileId[9]}${profileId[10]}${profileId[11]}"

    val startRed = "${profileId[12]}${profileId[13]}${profileId[14]}".toInt()
    val startGreen = "${profileId[15]}${profileId[16]}${profileId[17]}".toInt()
    val startBlue = "${profileId[18]}${profileId[19]}${profileId[20]}".toInt()

    val endRed = "${profileId[21]}${profileId[22]}${profileId[23]}".toInt()
    val endGreen = "${profileId[24]}${profileId[25]}${profileId[26]}".toInt()
    val endBlue = "${profileId[27]}${profileId[28]}${profileId[29]}".toInt()

    val startRedString = "${profileId[12]}${profileId[13]}${profileId[14]}"
    val startGreenString = "${profileId[15]}${profileId[16]}${profileId[17]}"
    val startBlueString = "${profileId[18]}${profileId[19]}${profileId[20]}"

    val endRedString = "${profileId[21]}${profileId[22]}${profileId[23]}"
    val endGreenString = "${profileId[24]}${profileId[25]}${profileId[26]}"
    val endBlueString = "${profileId[27]}${profileId[28]}${profileId[29]}"

    val backgroundShuffleSeed = ("${profileId[30]}${profileId[31]}${profileId[32]}${profileId[33]}${profileId[34]}${profileId[35]}").toInt()
    val startColor = Color(red = startRed, green = startGreen, blue = startBlue)
    val endColor = Color(red = endRed, green = endGreen, blue = endBlue)

    return fullImageProcess(
        initials = initials, userId = iD, color1 = "$startRedString$startGreenString$startBlueString", color2 = "$endRedString$endGreenString$endBlueString",
        width = 1200, height = 1200, startColor = startColor, endColor = endColor,
        textColor = Color.White, shuffleSeed = backgroundShuffleSeed.toLong()
    )
}

fun fullImageProcess(
                    initials: String,
                    userId: String,
                    color1: String,
                    color2: String,
                    width: Int,
                    height: Int,
                    startColor: Color,
                    endColor: Color,
                    textColor: Color,
                    shuffleSeed: Long
): Bitmap{
    val response = setBackground(width, height, startColor, endColor)
    val encoder = ImageEncoder()
    val design = encoder.reassembleImage(8, encoder.shuffleBitmap(encoder.splitImageToParts(8, response), shuffleSeed))
    return setTextsOnBackground(design, initials, userId, color1, color2, "$shuffleSeed",
        180f, 100f, 100f, 100f, 120f, textColor)
}

fun setTextsOnBackground(
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
    textColor: Color
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
                this.typeface = Typeface.DEFAULT_BOLD
            } else if (index == 4){
                this.typeface = Typeface.DEFAULT_BOLD
            }else this.typeface = Typeface.DEFAULT
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

fun createSeed(): Int{
    return (100000..999999).random()
}
fun createColorSeed(): String{
    val x = (1..255).random()
    return if (x < 10){
        "00$x"
    }else if (x in 10..99){
        "0$x"
    }else "$x"
}

fun verifyUser2(userImage: Bitmap, userId: String): Boolean{
    //
    var retrievedId = ""
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    val image = InputImage.fromBitmap(userImage, 0)
    recognizer.process(image)
        .addOnSuccessListener { visionText ->
            for (block in visionText.textBlocks) {
                val blockText = block.text
                retrievedId = "$retrievedId$blockText".replace(" ", "")
            }
        }

    return retrievedId == userId
}

fun verifyUser3(userImage: Bitmap, userId: String): Bitmap{
    //
    var retrievedId = ""
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    val image = InputImage.fromBitmap(userImage, 0)
    recognizer.process(image)
        .addOnSuccessListener { visionText ->
            for (block in visionText.textBlocks) {
                val blockText = block.text
                retrievedId = "$retrievedId$blockText".replace(" ", "")
            }
        }

    //return retrievedId
    return userImage
}

fun verifyUser(userImage: Bitmap, userId: String): String{
    //
    var retrievedId = ""
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    val image = InputImage.fromBitmap(userImage, 0)
    recognizer.process(image)
        .addOnSuccessListener { visionText ->
            for (block in visionText.textBlocks) {
                val blockText = block.text
                retrievedId = "$retrievedId$blockText".replace(" ", "")
            }
        }

    //return retrievedId
    return "I am a man"
}

fun getUserByImageId(userImage: Bitmap): String{
    //
    return ""
}
*/

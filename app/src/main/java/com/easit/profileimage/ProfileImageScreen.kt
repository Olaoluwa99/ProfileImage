package com.easit.profileimage

import android.graphics.Typeface
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.easit.profileimage.model.createSeed
import com.easit.profileimage.model.fullImageProcess
import com.easit.profileimage.model.fullImageProcess2
import com.easit.profileimage.model.getProfileById
import com.easit.profileimage.model.getProfileByIdM
import com.easit.profileimage.model.getProfileByIdN
import com.easit.profileimage.model.splitImageToParts
import com.easit.profileimage.model.splitImageToPartsHorizontal
import com.easit.profileimage.ui.theme.ProfileImageTheme
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@Composable
fun Profile() {

    val profileImageBitmap3 = fullImageProcess2(
        text = "8", width = 200, height = 200, startColor = Color.DarkGray, endColor = Color.LightGray,
        textColor = Color.White, textSize = 20f, typeface = Typeface.MONOSPACE, 123456
    )

    val profileImageBitmap2 = fullImageProcess(
        text = "IO", width = 200, height = 200, startColor = Color.DarkGray, endColor = Color.LightGray,
        textColor = Color.White, textSize = 100f, typeface = Typeface.MONOSPACE, 123456
    )
    //0xFF42A5F5
    //val profileImageBitmap4 = getProfileById("IO0xFFFFFF000xFFFF00FF123456")
    val profileImageBitmap = getProfileById("IO225123115089124012123456")

    //val profileImageBitmap4 = getProfileByIdM("EAS34a34rh51215103125089144112${createSeed()}")
    val profileImageBitmap4 = getProfileByIdN("EAS34a34rh51215103125089144112123456")
    
    val scrollState = rememberScrollState()

    var textValue by remember { mutableStateOf("") }
    var textBlockCount by remember { mutableStateOf(0) }
    var textLineCount by remember { mutableStateOf(0) }
    var textCharCount by remember { mutableStateOf(0) }

    //val k = splitImageToParts(6, profileImageBitmap4)[]

    Column (
        modifier = Modifier
            .padding(24.dp)
            .verticalScroll(scrollState)
    ){
        Image(
            bitmap = profileImageBitmap.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(35.dp)),
            contentAlignment = Alignment.Center
        ){
            Image(
                bitmap = profileImageBitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(35.dp)),
            contentAlignment = Alignment.Center
        ){
            Image(
                bitmap = profileImageBitmap4.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
            )
        }

        Button(
            onClick = {
                val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                //val image = InputImage.fromBitmap(k, 0)

                var newText = ""
                val image = InputImage.fromBitmap(profileImageBitmap4, 0)
                recognizer.process(image)
                    .addOnSuccessListener { visionText ->
                        //textValue = visionText.text//.replace(" ", "")
                        //textCharCount = textValue.length

                        for (block in visionText.textBlocks) {
                            val blockText = block.text
                            textValue = "$textValue$blockText".replace(" ", "")
                            textCharCount = textValue.length
                        }
                        //textValue = newText
                    }

                /*val result = recognizer.process(image)
                    .addOnSuccessListener { visionText ->
                        textValue = visionText.text
                        textBlockCount = visionText.textBlocks.size
                        textLineCount = visionText.textBlocks[0].lines.size
                        textCharCount = visionText.textBlocks[0].lines[0].elements.size



                        for (block in visionText.textBlocks) {
                            val blockText = block.text
                            //textValue = "$fx, ${block.text} "
                            //val blockCornerPoints = block.cornerPoints
                            val blockFrame = block.boundingBox
                            for (line in block.lines) {
                                val lineText = line.text
                                val lineCornerPoints = line.cornerPoints
                                val lineFrame = line.boundingBox
                                for (element in line.elements) {
                                    val elementText = element.text
                                    val elementCornerPoints = element.cornerPoints
                                    val elementFrame = element.boundingBox
                                }
                            }
                        }
                    }
                    .addOnFailureListener { e ->
                        //
                    }*/

                //textBlockCount = "${result.result.textBlocks.size}"
                //textLineCount = "${result.result.textBlocks[0].lines.size}"
                //textCharCount = "${result.result.textBlocks[0].lines[0].elements.size}"

                /*val resultText = result.result.text
                for (block in result.result.textBlocks) {
                    val blockText = block.text
                    val blockCornerPoints = block.cornerPoints
                    val blockFrame = block.boundingBox
                    for (line in block.lines) {
                        val lineText = line.text
                        val lineCornerPoints = line.cornerPoints
                        val lineFrame = line.boundingBox
                        for (element in line.elements) {
                            val elementText = element.text
                            val elementCornerPoints = element.cornerPoints
                            val elementFrame = element.boundingBox
                        }
                    }
                }*/

            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black
            ),
            contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp, start = 24.dp, end = 24.dp),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Text(text = "Submit")
        }

        /*Image(
            bitmap = k.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
        )*/
        
        Text(text = textValue)
        Text(text = "textCharCount = $textCharCount")
        /*
        Text(text = "textBlockCount = $textBlockCount")
        Text(text = "textLineCount = $textLineCount")
        Text(text = "textCharCount = $textCharCount")*/
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    ProfileImageTheme {
        Profile()
    }
}
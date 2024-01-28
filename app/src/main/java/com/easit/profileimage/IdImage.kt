package com.easit.profileimage

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.easit.profileimage.model.createBitmapWithWhiteBackground
import com.easit.profileimage.ui.theme.ProfileImageTheme
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
/*
@Composable
fun IdImage(
    onRetrieveIdValue: (String) -> Unit = {},
) {
    val testValue by remember { mutableStateOf("EAS34a34rh51215103125089144112123456") }
    val profileImageBitmap by remember { mutableStateOf(getProfileById(testValue)) }
    //val profileImageBitmap = getProfileById("EAS34a34rh51${createColorSeed()}${createColorSeed()}${createColorSeed()}${createColorSeed()}${createColorSeed()}${createColorSeed()}${createSeed()}")

    var imageValue by remember { mutableStateOf("") }

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
                .clickable {
                    //
                    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
                    val image = InputImage.fromBitmap(profileImageBitmap, 0)
                    recognizer.process(image)
                        .addOnSuccessListener { visionText ->
                            for (block in visionText.textBlocks) {
                                val blockText = block.text
                                imageValue = "$imageValue$blockText".replace(" ", "")
                            }
                        }
                }
        )
    }
    fun onRetrieveIdValue(){

    }
}

@Preview(showBackground = true)
@Composable
fun IdImagePreview() {
    ProfileImageTheme {
        IdImage()
    }
}

*/
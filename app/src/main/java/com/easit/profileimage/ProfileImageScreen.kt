package com.easit.profileimage

import android.graphics.Bitmap
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import com.easit.id_image.IdImage
import com.easit.profileimage.model.IdImage
import com.easit.profileimage.model.createBitmapWithWhiteBackground
import com.easit.profileimage.model.createColorSeed
import com.easit.profileimage.model.createSeed
import com.easit.profileimage.model.getProfileById
import com.easit.profileimage.model.getProfileByIdOld
import com.easit.profileimage.model.verifyUser
import com.easit.profileimage.ui.theme.ProfileImageTheme
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

@Composable
fun Profile() {

    //Create Image bitmap based on Id - Input Proposed ID and Image is generated based on it--
    //Get image from ID - Retrieves bitmap based on ID input--
    //Get ID from Image - Retrieve ID based on bitmap input--

    //Verify ID based on Image - Input Image and input id and verify if image matches ID

    val profileImageBitmapOld = getProfileByIdOld("IO225123115089124012123456")

    //val profileImageBitmap4 = getProfileByIdM("EAS34a34rh51215103125089144112${createSeed()}")

    val testValue by remember { mutableStateOf("EASY|34a34rh51789123321215103125089144112123456") }
    //val profileImageBitmap = getProfileById(testValue)
    //val profileImageBitmap by remember { mutableStateOf(getProfileById(testValue)) }
    //val profileImageBitmap2 = getProfileById("EAS34a34rh51${createColorSeed()}${createColorSeed()}${createColorSeed()}${createColorSeed()}${createColorSeed()}${createColorSeed()}${createSeed()}")
    var testResult by remember { mutableStateOf("") }


    val scrollState = rememberScrollState()

    val idImage = IdImage()
    val profileImageBitmap by remember { mutableStateOf(idImage.getImageFromId(testValue)) }
    val yy = idImage.createUserId(
        "", 0, 0, 0, 0, 0, 0,
        "", 0L
    )

        //
    Column (
        modifier = Modifier
            .padding(24.dp)
            .verticalScroll(scrollState)
    ){
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(35.dp)),
            contentAlignment = Alignment.Center
        ){
            Image(
                bitmap = profileImageBitmapOld.asImageBitmap(),
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
                bitmap = profileImageBitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                testResult = ""
                val result = idImage.retrieveIdByImage(profileImageBitmap)
                result.addOnSuccessListener {
                    for (block in it.textBlocks) {
                        val blockText = block.text
                        testResult = "$testResult$blockText".replace(" ", "")
                    }
                }
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
        
        //Text(text = textValue)
        Text(text = "Test result = $testResult")
        //Text(text = "textCharCount = $textCharCount")
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    ProfileImageTheme {
        Profile()
    }
}
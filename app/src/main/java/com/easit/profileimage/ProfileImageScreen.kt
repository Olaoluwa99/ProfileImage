package com.easit.profileimage

import android.graphics.Typeface
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
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
import com.easit.profileimage.model.getProfileById2
import com.easit.profileimage.model.getProfileByIdM
import com.easit.profileimage.ui.theme.ProfileImageTheme

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

    val profileImageBitmap4 = getProfileByIdM("EAS34a34rh51215103125089144112${createSeed()}")

    Column (modifier = Modifier.padding(24.dp)){
        Image(
            bitmap = profileImageBitmap.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
            //.padding(20.dp)
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
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    ProfileImageTheme {
        Profile()
    }
}
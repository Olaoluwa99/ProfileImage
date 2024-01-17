package com.easit.profileimage.model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import kotlin.random.Random

class ImageDecoder {
    fun fullUnShuffle(bitmap: Bitmap, userSeed: Long): Bitmap{
        val split = splitImageToParts(bitmap)
        val unShuffled = unShuffle(split, userSeed)
        return reassembleImage(unShuffled)
    }

    fun fullUnRotateAndUnShuffle(bitmap: Bitmap, userSeed: Long, indexList: MutableList<Int>) : Bitmap{
        val split = splitImageToParts(bitmap)
        val unShuffled = unShuffle(split, userSeed)
        val reset = resetImageRotation(unShuffled as MutableList<Bitmap>, indexList)
        return reassembleImage(reset)
    }

    private fun splitImageToParts(bitmap: Bitmap): List<Bitmap> {
        val imageWidth = bitmap.width
        val imageHeight = bitmap.height

        val tileWidth = imageWidth / 4
        val tileHeight = imageHeight / 4
        val tiles = mutableListOf<Bitmap>()

        // Split image into 16 parts
        for (y in 0 until 4) {
            for (x in 0 until 4) {
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

    private fun reassembleImage(tiles: List<Bitmap>): Bitmap {
        // Reassemble the image into a new bitmap
        val imageWidth = tiles[0].width * 4
        val imageHeight = tiles[0].height * 4
        val shuffledBitmap = Bitmap.createBitmap(imageWidth, imageHeight, tiles[0].config)
        val canvas = Canvas(shuffledBitmap)

        for (y in 0 until 4) {
            for (x in 0 until 4) {
                val partIndex = y * 4 + x
                val partBitmap = tiles[partIndex]
                canvas.drawBitmap(partBitmap, (x * partBitmap.width).toFloat(), (y * partBitmap.height).toFloat(), null)
            }
        }

        return shuffledBitmap
    }

    private fun rotateBitmap(bitmap: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun bitmapToInputStream(bitmap: Bitmap): InputStream {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream) // Adjust compression format and quality as needed
        return ByteArrayInputStream(byteArrayOutputStream.toByteArray())
    }

    fun unShuffle(splitImageList: List<Bitmap>, userSeed: Long): List<Bitmap>{
        val splitCount = splitImageList.size

        //Shuffle index
        val numberList = mutableListOf<Int>()
        for (i in 0 until splitCount) {
            numberList.add(i)
        }
        val rand = Random(userSeed)//Random
        val shuffledNumberList = numberList.shuffled(rand)

        //Create index list
        val indexList = mutableListOf<Int>()
        for (element in numberList){
            shuffledNumberList.forEachIndexed { index, value ->
                if (element == value) indexList.add(index)
            }
        }

        //UnShuffle list with received index
        val unShuffledImageList = mutableListOf<Bitmap>()
        for (i in 0 until splitImageList.size - 0) {
            unShuffledImageList.add(splitImageList[(indexList[i] + 0)])
        }
        return unShuffledImageList
    }

    private fun resetImageRotation(splitImageList: MutableList<Bitmap>, rotateIndex: List<Int>): List<Bitmap>{
        val fixedList = mutableListOf<Bitmap>()
        rotateIndex.forEachIndexed { index, value ->
            if (value == 1){
                //Rotate and add to list
                val fixedItem = rotateBitmap(splitImageList[index], -180f)
                fixedList.add(fixedItem)
            }else{
                //Add to list
                fixedList.add(splitImageList[index])
            }
        }
        return fixedList
    }
}
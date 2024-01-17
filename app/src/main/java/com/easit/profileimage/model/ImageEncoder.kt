package com.easit.profileimage.model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import kotlin.random.Random

class ImageEncoder {

    var tileState = mutableListOf(
        0, 0, 0, 0,
        0, 0, 0, 0,
        0, 0, 0, 0,
        0, 0, 0, 0
    )
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

    fun rotateTiles(tiles: MutableList<Bitmap>): RotateTileResult {
        val rotateThreshold = 0.5 // Change this to adjust the rotation probability
        tiles.forEachIndexed { index, tile ->
            if (Math.random() < rotateThreshold) {
                tiles[index] = rotateBitmap(tile, 180f) // Rotate 180 degrees (adjust as needed)
                //Add rotated tile index to
                tileState[index] = 1
            }
        }
        return RotateTileResult(tileState, tiles)
    }

    fun shuffleBitmap(tiles: List<Bitmap>, userSeed: Long): List<Bitmap> {
        val random = Random(userSeed)//Random
        val tilesCopy = tiles.toMutableList()
        return tilesCopy.shuffled(random)
    }

    fun reassembleImage(imagePartsRoot: Int, tiles: List<Bitmap>): Bitmap {
        // Reassemble the image into a new bitmap
        val imageWidth = tiles[0].width * imagePartsRoot
        val imageHeight = tiles[0].height * imagePartsRoot
        val shuffledBitmap = Bitmap.createBitmap(imageWidth, imageHeight, tiles[0].config)
        val canvas = Canvas(shuffledBitmap)

        for (y in 0 until imagePartsRoot) {
            for (x in 0 until imagePartsRoot) {
                val partIndex = y * imagePartsRoot + x
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
}

data class RotateTileResult(
    val shuffledListIndex: MutableList<Int>,
    val tile: MutableList<Bitmap>
)
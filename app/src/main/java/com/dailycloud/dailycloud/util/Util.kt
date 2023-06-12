package com.dailycloud.dailycloud.util

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.dailycloud.dailycloud.R
import com.google.firebase.Timestamp
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Util {
    fun reduceText(text : String, maxLength : Int) : String{

        return if (text.length > maxLength) {
             text.substring(0, maxLength) + "..."
        } else {
            text
        }
    }

    fun dateFormat(timestamp: Timestamp): String {
        val currentDate = timestamp.toDate()
        val dateFormat = SimpleDateFormat("dd MMMM yyyy")

        return dateFormat.format(currentDate)

    }

    fun Long.toDate(): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return dateFormat.format(Date(this))
    }

    fun Long.toDay(): Int {
        val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
        return dateFormat.format(Date(this)).toInt()
    }

    fun Long.toMonth(): Int {
        val dateFormat = SimpleDateFormat("MM", Locale.getDefault())
        return dateFormat.format(Date(this)).toInt()
    }

    fun Long.toYear(): Int {
        val dateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        return dateFormat.format(Date(this)).toInt()
    }

    fun Int.dpToPx(): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }

    fun createFile(application: Application): File {
        val timeStamp: String = SimpleDateFormat(
            "dd-MMM-yyyy",
            Locale.US
        ).format(System.currentTimeMillis())

        val mediaDir = application.externalMediaDirs.firstOrNull()?.let {
            File(it, application.resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        val outputDirectory = if (
            mediaDir != null && mediaDir.exists()
        ) mediaDir else application.filesDir

        return File(outputDirectory, "$timeStamp.jpg")
    }

    fun Context.createImageFile(): File {
        val timeStamp: String = SimpleDateFormat(
            "dd-MMM-yyyy",
            Locale.US
        ).format(System.currentTimeMillis())

        return File.createTempFile(
            timeStamp,
            ".jpg",
            externalCacheDir
        )
    }

    fun reduceFileImage(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)

        var compressQuality = 100
        var streamLength: Int

        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        } while (streamLength > 1000000)

        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))

        return file
    }
}


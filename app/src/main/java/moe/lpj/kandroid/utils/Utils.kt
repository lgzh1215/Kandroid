package moe.lpj.kandroid.utils

import android.os.Environment

fun isExternalStorageWritable(): Boolean = Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()

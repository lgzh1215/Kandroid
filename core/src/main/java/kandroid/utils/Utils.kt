package kandroid.utils

import java.math.BigInteger
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

private val DATE_FORMAT = SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.getDefault())

val Date.utilFormat: String get() = DATE_FORMAT.format(this)

fun <T> T?.requireNonNull(): T = this ?: throw NullPointerException()

val String.utilMd5: String get() = BigInteger(1, MessageDigest.getInstance("MD5").digest(this.toByteArray())).toString(16)
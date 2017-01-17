package kandroid.utils

import java.math.BigInteger
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

private val format_yyyyMMdd_HHmmssSSS: ThreadLocal<SimpleDateFormat> = object : ThreadLocal<SimpleDateFormat>() {
    override fun initialValue(): SimpleDateFormat {
        return SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.getDefault())
    }
}

private val format_yyyyMMdd: ThreadLocal<SimpleDateFormat> = object : ThreadLocal<SimpleDateFormat>() {
    override fun initialValue(): SimpleDateFormat {
        return SimpleDateFormat("yyyyMMdd", Locale.getDefault())
    }
}

val Date.yyyyMMdd_HHmmssSSS: String get() = format_yyyyMMdd_HHmmssSSS.get().format(this)
val Date.yyyyMMdd: String get() = format_yyyyMMdd.get().format(this)

fun <T> T?.requireNonNull(): T = this ?: throw NullPointerException()

val String.utilMd5: String get() = BigInteger(1, MessageDigest.getInstance("MD5").digest(this.toByteArray())).toString(16)
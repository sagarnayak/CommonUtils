package com.sagar.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES
import android.provider.Settings
import java.text.NumberFormat
import java.util.*
import kotlin.math.max
import kotlin.math.min


@Suppress("unused")
class AppUtil(private val context: Context) {

    @SuppressLint("QueryPermissionsNeeded")
    fun appInstalledOrNot(uri: String): Boolean {
        val pm: PackageManager = context.packageManager
        val packageInfoList =
            pm.getInstalledPackages(PackageManager.GET_ACTIVITIES)
        for (packageInfo in packageInfoList) {
            val packageName = packageInfo.packageName
            if (packageName != null && packageName == uri) {
                return true
            }
        }
        return false
    }

    fun getAppVersionName(): String {
        val packageInfo = context.packageManager.getPackageInfo(
            context.packageName,
            PackageManager.GET_ACTIVITIES
        )
        return packageInfo.versionName
    }

    fun getAppVersionCode(): Int {
        val packageInfo = context.packageManager.getPackageInfo(
            context.packageName,
            PackageManager.GET_ACTIVITIES
        )
        return if (Build.VERSION.SDK_INT >= VERSION_CODES.P) {
            packageInfo.longVersionCode.toInt()
        } else {
            @Suppress("DEPRECATION")
            packageInfo.versionCode
        }
    }

    fun makePhoneCall(number: String) {
        context.startActivity(
            Intent(
                Intent.ACTION_DIAL,
                Uri.parse("tel:${number}")
            )
        )
    }

    @SuppressLint("HardwareIds")
    fun getUniqueId(): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    fun getUUID(): String {
        return UUID.randomUUID().toString()
    }

    fun getDeviceInformation(): String {
        val reqString = ("Native Android" + " " + Build.VERSION.RELEASE
                + ";" + Build.MANUFACTURER
                + ";" + Build.MODEL + ";" + VERSION_CODES::class.java.fields[Build.VERSION.SDK_INT].name + ";" + "true")
        println("deviceName==$reqString")
        return reqString
    }

    fun getTimeZoneCurrentCountryDevice(): String {
        val tz = TimeZone.getDefault()
        return tz.id
    }

    fun getSystemLocale(): Locale {
        return if (Build.VERSION.SDK_INT >= VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {
            @Suppress("DEPRECATION")
            context.resources.configuration.locale
        }
    }

    fun getDeviceCountryCode(): String {
        return getSystemLocale().country
    }

    fun formatNumber(number: Any?, locale: Locale = Locale.getDefault()): String {
        return try {
            number?.let {
                val toFormatNumber = number.toString().toDouble()
                val mNumberFormat: NumberFormat = NumberFormat.getInstance(locale)
                mNumberFormat.maximumFractionDigits = 2
                mNumberFormat.format(toFormatNumber)
            } ?: run {
                return ""
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            number.toString()
        }
    }

    fun lighten(color: Int, fraction: Double): Int {
        var red: Int = Color.red(color)
        var green: Int = Color.green(color)
        var blue: Int = Color.blue(color)
        red = lightenColor(red, fraction)
        green = lightenColor(green, fraction)
        blue = lightenColor(blue, fraction)
        val alpha: Int = Color.alpha(color)
        return Color.argb(alpha, red, green, blue)
    }

    fun darken(color: Int, fraction: Double): Int {
        var red = Color.red(color)
        var green = Color.green(color)
        var blue = Color.blue(color)
        red = darkenColor(red, fraction)
        green = darkenColor(green, fraction)
        blue = darkenColor(blue, fraction)
        val alpha = Color.alpha(color)
        return Color.argb(alpha, red, green, blue)
    }

    private fun lightenColor(color: Int, fraction: Double): Int {
        return min(color + color * fraction, 255.0).toInt()
    }

    private fun darkenColor(color: Int, fraction: Double): Int {
        return max(color - color * fraction, 0.0).toInt()
    }
}
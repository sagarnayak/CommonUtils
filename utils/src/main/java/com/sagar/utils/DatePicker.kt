package com.sagar.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import java.util.*

/*
to define a custom theme for the date picker dialog use below template

<style name="DatePickerStyle" parent="ThemeOverlay.AppCompat.Dialog">
    <item name="colorAccent">@color/appColor</item><!--header background-->
    <item name="android:windowBackground">@color/white</item><!--calendar background-->
    <item name="android:colorControlActivated">@color/appColor</item><!--selected day-->
    <item name="android:textColorPrimary">@color/textPrimary</item><!--days of the month-->
    <item name="android:textColorSecondary">@color/black</item><!--days of the week-->
</style>
 */
object DatePicker {

    fun show(
        context: Context,
        theme: Int,
        callback: (year: Int, month: Int, date: Int) -> Unit
    ) {
        DatePickerDialog(
            context,
            theme,
            { _: DatePicker, year: Int, month: Int, date: Int ->
                callback(year, month, date)
            },
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}
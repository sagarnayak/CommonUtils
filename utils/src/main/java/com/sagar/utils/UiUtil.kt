package com.sagar.utils

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.text.Html
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView


/**
 * created by SAGAR KUMAR NAYAK
 * util class to perform any kind of UI operations
 *
 *
 * exposed methods
 * [.hideSoftKeyboard]
 * [.hideSoftKeyboardAtStart]
 */
@Suppress("unused")
object UiUtil {

    /**
     * method to hide the soft keyboard
     *
     * @param context context
     */
    fun hideSoftKeyboard(context: Context) {
        val view = (context as Activity).currentFocus
        if (view != null) {
            val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    /**
     * method to set the activity to not to show the soft keyboard at activity start.
     * to use this method call it before [Activity.setContentView] in Activity
     *
     * @param context context
     */
    fun hideSoftKeyboardAtStart(context: Context) {
        (context as Activity).window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    /**
     * method to set the activity to stay wake when running. this will not allow the android device
     * to go to sleep when the activity is running.
     *
     * @param context activity context
     */
    fun doNotAllowForSleep(context: Context) {
        (context as Activity).window
            .addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    /**
     * method to make the layout full screen with the status bar as blurred.
     *
     * @param context activity context
     */
    fun makeFullScreen(context: Context) {
        (context as Activity).window.apply {
//            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            statusBarColor = Color.TRANSPARENT

            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * method to get flags for clearing current stack and start new activity
     */
    fun clearStackAndStartNewActivity(intent: Intent): Intent {
        return intent.setFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        )
    }

    fun parseToHTML(rawHtml: String, textView: TextView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.text =
                Html.fromHtml(rawHtml, Html.FROM_HTML_MODE_COMPACT)
        } else {
            @Suppress("DEPRECATION")
            textView.text == Html.fromHtml(rawHtml)
        }
    }

    /**
     * this will convert the country code GB to its country flag.
     */
    fun convertLocaleToFlagEmoji(locale: String): String {
        val firstLetter = Character.codePointAt(locale, 0) - 0x41 + 0x1F1E6
        val secondLetter = Character.codePointAt(locale, 1) - 0x41 + 0x1F1E6
        return String(Character.toChars(firstLetter)) + String(
            Character.toChars(
                secondLetter
            )
        )
    }
}

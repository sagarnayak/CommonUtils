@file:Suppress("unused")

package com.sagar.utils

import android.graphics.Color
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import com.sagar.utils.StringUtil.TextType.CLICKABLE
import com.sagar.utils.StringUtil.TextType.NOT_CLICKABLE
import java.util.*
import kotlin.random.Random

class StringUtil {
    companion object {
        private const val ALLOWED_CHARACTERS =
            "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM"

        fun getRandomString(sizeOfRandomString: Int = Random.nextInt(5, 120)): String {
            val sb = StringBuilder(sizeOfRandomString)
            for (i in 0 until sizeOfRandomString) {
                sb.append(ALLOWED_CHARACTERS[Random.nextInt(ALLOWED_CHARACTERS.length)])
                if (Random.nextInt(0, 5) == 0)
                    sb.append(" ")
            }
            return sb.toString()
        }

        fun generatePartialClickableTextView(
            textListToOperate: ArrayList<PartialText>,
            textViewToApplyOn: TextView,
            selectedTextColor: Int
        ) {
            if (textListToOperate.size == 0)
                return

            val spannableStringBuilder = SpannableStringBuilder()

            textListToOperate.forEach { item ->
                val startIndex = spannableStringBuilder.lastIndex + 1
                val endIndex = startIndex + item.textToProcess.length
                spannableStringBuilder.append(item.textToProcess)
                if (item.textType == CLICKABLE) {
                    spannableStringBuilder.setSpan(
                        ClickableSpan(selectedTextColor, (item as ClickableText).callback),
                        startIndex,
                        endIndex,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }

            textViewToApplyOn.apply {
                text = spannableStringBuilder
                movementMethod = LinkMovementMethod.getInstance()
                highlightColor = Color.TRANSPARENT
            }
        }

        fun generatePartialClickableTextView(
            content: String,
            stringToMakeClickable: String,
            textViewToApplyOn: TextView,
            selectedTextColor: Int,
            callback: () -> Unit
        ) {
            if (!content.contains(stringToMakeClickable, true))
                return

            val ss =
                SpannableString(content)
            val cs = ClickableSpan(selectedTextColor, callback)
            ss.setSpan(
                cs,
                content.indexOf(stringToMakeClickable),
                content.indexOf(stringToMakeClickable) + stringToMakeClickable.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            textViewToApplyOn.apply {
                text = ss
                movementMethod = LinkMovementMethod.getInstance()
                highlightColor = Color.TRANSPARENT
            }
        }

        fun makeStringPretty(stringToProcess: String, applyToAllWords: Boolean = false): String {
            if (stringToProcess.isEmpty())
                return stringToProcess

            val splits = stringToProcess.split(" ")
            if (splits.size > 1 && applyToAllWords) {
                var toReturn = ""
                splits.forEach { word ->
                    if (word.isNotEmpty())
                        toReturn =
                            "$toReturn${if (toReturn.isNotEmpty()) " " else ""}${
                                word.substring(
                                    0,
                                    1
                                )
                                    .toUpperCase(Locale.ROOT)
                            }${word.substring(1)}"
                }
                return toReturn
            }

            return "${
                stringToProcess.substring(0, 1)
                    .toUpperCase(Locale.ROOT)
            }${stringToProcess.substring(1)}"
        }
    }

    enum class TextType {
        CLICKABLE, NOT_CLICKABLE
    }

    abstract class PartialText(val textType: TextType, val textToProcess: String) {
    }

    class ClickableText(textToProcess: String, val callback: () -> Unit) :
        PartialText(CLICKABLE, textToProcess) {

    }

    class NonClickableText(textToProcess: String) :
        PartialText(NOT_CLICKABLE, textToProcess) {

    }

    class ClickableSpan(
        private val selectedTextColor: Int,
        private val callback: () -> Unit
    ) :
        android.text.style.ClickableSpan() {
        override fun onClick(widget: View) {
            callback()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
            ds.color = selectedTextColor
        }
    }
}
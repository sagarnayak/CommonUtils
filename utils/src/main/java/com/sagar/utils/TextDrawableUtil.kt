package com.sagar.utils

import android.graphics.drawable.Drawable
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import java.util.*

/*
created by SAGAR KUMAR NAYAK
class to create place holder for image views.
dependencies -
project level -
maven { url 'http://dl.bintray.com/amulyakhare/maven' }
app level gradle -
implementation 'com.amulyakhare:com.amulyakhare.textdrawable:1.0.1'
how to use -
to ge the place holder call the getPlaceHolder method with the string to create
the image and the shape you want to create.
*/
object TextDrawableUtil {
    fun getPlaceHolder(stringToShow: String, shape: Shape?): Drawable? {
        val placeHolderToShow = processStringToShow(stringToShow)
        when (shape) {
            Shape.ROUND -> return TextDrawable.builder()
                .buildRound(
                    placeHolderToShow.toUpperCase(Locale.ROOT),
                    ColorGenerator.MATERIAL.getColor(
                        placeHolderToShow
                    )
                )
            Shape.RECTANGLE -> return TextDrawable.builder()
                .buildRect(
                    placeHolderToShow.toCharArray()[0]
                        .toString().toUpperCase(Locale.ROOT),
                    ColorGenerator.MATERIAL.getColor(
                        placeHolderToShow
                    )
                )
        }
        return null
    }

    enum class Shape {
        ROUND, RECTANGLE
    }

    private fun processStringToShow(stringToShow: String): String {
        if (stringToShow.length == 1)
            return stringToShow

        val splits = stringToShow.split(" ")
        if (splits.size > 1) {
            return "${splits[0].substring(0, 1)}${splits[splits.size - 1].substring(0, 1)}"
        }

        return stringToShow.substring(0, 2)
    }
}
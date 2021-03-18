package com.sagar.utils.transformations

import android.graphics.Bitmap

class TransformationMaster {

    fun circleTransform(source: Bitmap): Bitmap {
        return CircleTransformation().transform(source)
    }

    fun circleTransformWithBorder(borderColor: Int, source: Bitmap?): Bitmap {
        return CircleTransformWithBorder(borderColor).transform(source)
    }
}
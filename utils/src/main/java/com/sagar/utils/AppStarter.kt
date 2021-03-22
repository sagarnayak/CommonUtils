package com.sagar.utils

import android.content.Context
import android.content.Intent
import com.sagar.modelsandenums.KeywordsAndConstants.END_SELF

class AppStarter(private val context: Context) {

    fun <T> restartApp(targetSplashActivity: Class<T>) {
        context.startActivity(
            Intent(
                context,
                targetSplashActivity
            )
                .setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK
                )
        )
        context.sendBroadcast(Intent(END_SELF))
    }

    fun endAll() {
        context.sendBroadcast(
            Intent(
                END_SELF
            )
        )
    }
}
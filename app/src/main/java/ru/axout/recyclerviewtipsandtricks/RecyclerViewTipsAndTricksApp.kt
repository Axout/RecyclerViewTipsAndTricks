package ru.axout.recyclerviewtipsandtricks

import android.app.Application
import timber.log.Timber

class RecyclerViewTipsAndTricksApp: Application() {

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
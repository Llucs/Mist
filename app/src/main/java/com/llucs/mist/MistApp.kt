package com.llucs.mist

import android.app.Application
import com.llucs.mist.util.PreferencesManager

class MistApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        PreferencesManager.init(this)
    }

    companion object {
        lateinit var instance: MistApp
            private set
    }
}

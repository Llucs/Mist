package com.llucs.mist

import android.app.Application

class MistApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MistApp
            private set
    }
}

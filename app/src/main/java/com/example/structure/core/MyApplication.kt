package com.example.structure.core

import android.app.Application
import android.content.Context
import com.example.structure.locale.LocaleManager
import com.example.structure.util.Pref
import com.facebook.drawee.backends.pipeline.Fresco
import com.jakewharton.threetenabp.AndroidThreeTen

class MyApplication : Application() {

    companion object {
        lateinit var instance: MyApplication
    }

    override fun attachBaseContext(base: Context) {
        instance = this
        super.attachBaseContext(base)
        // Perform any locale setup or other configuration here if necessary
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)  // Unresolved reference: AndroidThreeTen

        //   FirebaseApp.initializeApp(this)
        Fresco.initialize(this)

        // Perform any additional setup after the instance is initialized
        val languageCode = Pref.getStringValue(Pref.PREF_LANGUAGE, "EN").lowercase()
        LocaleManager.setLocale(this, languageCode)
    }
}
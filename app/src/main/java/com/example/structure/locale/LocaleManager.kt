package com.example.structure.locale

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LocaleManager {

    fun setLocale(context: Context, language: String): Context {
        return updateResources(context, language)
    }

    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)
        // For RTL Support
        configuration.setLayoutDirection(locale)

        // Force LTR layout direction for all languages
      //  configuration.setLayoutDirection(Locale("en"))
        return context.createConfigurationContext(configuration)
    }
}
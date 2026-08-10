package com.elifnurozcelik.hw1

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LocaleHelper {
    fun getLanguage(context: Context): String {
        val pref = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return pref.getString("LANG", "en") ?: "en"
    }
    @SuppressLint("ApplySharedPref")
    fun setLanguage(context: Context, lang: String) {
        val pref = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        pref.edit().putString("LANG", lang).commit()
    }
    fun setLocale(context: Context, lang: String): Context {
        setLanguage(context, lang)
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}

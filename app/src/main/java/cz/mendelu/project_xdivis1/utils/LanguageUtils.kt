package cz.mendelu.project_xdivis1.utils

import java.util.*

object LanguageUtils {
    private val CZECH = "cs"
    private val SLOVAK = "sk"
    private val ENGLISH = "en"

    fun isLanguageCzech(): Boolean {
        val language = Locale.getDefault().language
        return language.equals(CZECH) || language.equals(SLOVAK) || language.equals(ENGLISH)
    }
}
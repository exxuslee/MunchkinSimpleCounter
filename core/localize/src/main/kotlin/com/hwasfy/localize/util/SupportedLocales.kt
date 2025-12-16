package com.hwasfy.localize.util

import com.hwasfy.localize.R
import java.util.Locale

enum class SupportedLocales(val tag: String, val locale: Locale, val icon: Int) {

    EN_US("en-US", Locale.forLanguageTag("en-US"), R.drawable.icon_32_flag_usa),
    RU_RU("ru-RU", Locale.forLanguageTag("ru-RU"), R.drawable.icon_32_flag_russia),
    EN_UK("en-UK", Locale.forLanguageTag("en-UK"), R.drawable.icon_32_flag_england),
    UR_PK("ur-PK", Locale.forLanguageTag("ur-PK"), R.drawable.icon_32_flag_india),
    FR_FR("fr-FR", Locale.forLanguageTag("fr-FR"), R.drawable.icon_32_flag_france),
    ES_ES("es-ES", Locale.forLanguageTag("es-ES"), R.drawable.icon_32_flag_spain),
    DE_DE("de-DE", Locale.forLanguageTag("de-DE"), R.drawable.icon_32_flag_germany),
    ZH_CN("zh-CN", Locale.forLanguageTag("zh-CN"), R.drawable.icon_32_flag_china),
    JA_JP("ja-JP", Locale.forLanguageTag("ja-JP"), R.drawable.icon_32_flag_japan),
    PT_BR("pt-BR", Locale.forLanguageTag("pt-BR"), R.drawable.icon_32_flag_brazil),
    HI_IN("hi-IN", Locale.forLanguageTag("hi-IN"), R.drawable.icon_32_flag_india),
    TR_TR("tr-TR", Locale.forLanguageTag("tr-TR"), R.drawable.icon_32_flag_turkey),
    FA_IR("fa-IR", Locale.forLanguageTag("fa-IR"), R.drawable.icon_32_flag_iran),
    KO_KR("ko-KR", Locale.forLanguageTag("ko-KR"), R.drawable.icon_32_flag_korea);

    companion object {
        fun fromTag(tag: String): SupportedLocales {
            return entries.find { it.tag.equals(tag, ignoreCase = true) }
                ?: throw IllegalArgumentException("Locale not supported: $tag")
        }
    }
}


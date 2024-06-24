package com.aos.goodideacard.enums

import androidx.appcompat.app.AppCompatDelegate

enum class BackgroundType(val code: Int) {
    SYSTEM(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),
    DAY(AppCompatDelegate.MODE_NIGHT_NO),
    DARK(AppCompatDelegate.MODE_NIGHT_YES)
}
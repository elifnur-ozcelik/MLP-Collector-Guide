package com.elifnurozcelik.hw1

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("charProgressText")
fun TextView.setCharName(progress: Int?) {
    val p = progress ?: 0
    text = when (p) {
        0 -> "Apple Jack"
        1 -> "Twilight Sparkle"
        2 -> "Rainbow Dash"
        3 -> "Pinkie Pie"
        4 -> "Rarity"
        else -> "Fluttershy"
    }
}
@BindingAdapter("charProgressImage")
fun ImageView.setCharImage(progress: Int?) {
    val p = progress ?: 0
    val res = when (p) {
        0 -> R.drawable.apple_jack
        1 -> R.drawable.twilight_sparkle
        2 -> R.drawable.rainbow_dash
        3 -> R.drawable.pinkie_pie
        4 -> R.drawable.rarity
        else -> R.drawable.fluttershy
    }
    setImageResource(res)
}

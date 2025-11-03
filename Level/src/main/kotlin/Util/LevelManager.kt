package Util

import kotlin.math.pow

fun getLevel(exp: Double): Int {
    if (exp >= 5 && exp <= 39) {
        return 1
    } else {
        return (exp / 15).pow(0.5).toInt()
    }
}

fun levelup(exp: Double): Boolean {
    val xp: Double = exp / 15
    val t = xp.pow(0.5)
    return t % 1.0 == 0.0
}
package de.fx.aggregatedtime

fun timeToString(minutes:Int):String {
    return "${minutes/60}:${minutes%60}"
}
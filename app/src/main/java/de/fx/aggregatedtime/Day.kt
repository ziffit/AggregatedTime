package de.fx.aggregatedtime

import java.io.Serializable
import java.time.LocalDate

class Day(var date: LocalDate, var aggegatedTime: Int, var calculatedTime: Int) : Serializable {

}
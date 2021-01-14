package com.c.digis.ui.main

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class ClaimsXAxisValueFormatter(private var timesList: List<String>) : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase): String {

        var position = value.roundToInt()
        val sdf = SimpleDateFormat("hh:mm", Locale.getDefault())
        if (value < 1 && value < 2) {
            position = 0
        } else if (value > 2 && value < 3) {
            position = 1
        } else if (value > 3 && value < 4) {
            position = 2
        } else if (value > 4 && value <= 5) {
            position = 3
        }
        return if (position < timesList.size) sdf.format(Date(
            getDateInMilliSeconds(timesList[position], "hh:mm"))) else ""
    }

    private fun getDateInMilliSeconds(givenDateString: String?, format: String): Long {
        val sdf = SimpleDateFormat(format, Locale.US)
        var timeInMilliseconds: Long = 1
        try {
            val mDate: Date = sdf.parse(givenDateString!!)!!
            timeInMilliseconds = mDate.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return timeInMilliseconds
    }
}
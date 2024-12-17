package com.example.structure.util

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.TemporalAdjusters
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

fun String.toDate(dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.ENGLISH)
    parser.timeZone = timeZone
    return parser.parse(this)!!
}

fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.ENGLISH)
    formatter.timeZone = timeZone
    return formatter.format(this)
}

fun TextView.attributedString(forText: String, foregroundColor: Int? = null, style: StyleSpan? = null) {
    val spannable: Spannable = SpannableString(text)

    // check if the text we're highlighting is empty to abort
    if (forText.isEmpty()) {
        return
    }

    // compute the start and end indices from the text
    val startIdx = text.indexOf(forText)
    val endIdx = startIdx + forText.length

    // if the indices are out of bounds, abort as well
    if (startIdx < 0 || endIdx > text.length) {
        return
    }

    // check if we can apply the foreground color
    foregroundColor?.let {
        spannable.setSpan(
            ForegroundColorSpan(it),
            startIdx,
            endIdx,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
    }

    // check if we have a style span
    style?.let {
        spannable.setSpan(
            style,
            startIdx,
            endIdx,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE
        )
    }
    // apply it
    text = spannable
}

fun getLastWeek(): Pair<String, String> {
    val today = LocalDate.now()
    val startOfLastWeek =
        today.minusWeeks(0).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val endOfLastWeek = startOfLastWeek.plusDays(6)
    return formatDateRange(startOfLastWeek, endOfLastWeek)
}

fun getLastMonth(): Pair<String, String> {
    val today = LocalDate.now()
    val startOfLastMonth = today.minusMonths(1).withDayOfMonth(1)
    val endOfLastMonth = startOfLastMonth.with(TemporalAdjusters.lastDayOfMonth())
    return formatDateRange(startOfLastMonth, endOfLastMonth)
}

fun getLastThreeMonths(): Pair<String, String> {
    val today = LocalDate.now()
    val startOfLastThreeMonths = today.minusMonths(3).withDayOfMonth(1)
    val endOfLastMonth = today.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth())
    return formatDateRange(startOfLastThreeMonths, endOfLastMonth)
}

fun formatDateRange(startDate: LocalDate, endDate: LocalDate): Pair<String, String> {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return Pair(startDate.format(formatter), endDate.format(formatter))
}